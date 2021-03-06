﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;
using System.Threading;

using Common;

using log4net;

namespace GameRegistry
{
    public class Registry : IDisposable
    {
        #region Private and Protected Data Members
        private static readonly ILog log = LogManager.GetLogger(typeof(Registry));
        private static Registry instance = null;
        private static object myLock = new object();

        private const int cleanupFrequency = 12000;
        private const int deadTimeout = 60000;

        protected Dictionary<int, GameInfo> games;
        private Int16 NextGameId = 1;
        protected Timer cleanupTimer;
        protected string fileName;
        protected int inCleanup = 0;
        #endregion

        #region Constructors and Instance Accessor
        protected Registry()
        {
            log.Debug("Creating a registry object");
            games = new Dictionary<int, GameInfo>();
            cleanupTimer = new Timer(Cleanup, null, cleanupFrequency, cleanupFrequency);
        }

        public void Dispose() { Dispose(true); }
        protected virtual void Dispose(bool flag)
        {
            if (games != null)
                games.Clear();
            if (cleanupTimer != null)
                cleanupTimer.Dispose();
            this.Dispose();
            GC.SuppressFinalize(this);
        }

        public static Registry Instance
        {
            get
            {
                lock (myLock)
                {
                    if (instance == null)
                        instance = new Registry();
                    return instance;
                }
            }
        }
        #endregion

        #region Public Methods
        public static void TakeDown()
        {
            lock (myLock)
            {
                if (instance != null)
                {
                    instance.Save();
                    instance.Dispose();
                    instance = null;
                }
            }
        }

        public Int16 GetProcessId()
        {
            return GetNextIdNumber();
        }

        public GameInfo RegisterGame(string label, Common.EndPoint publicEP)
        {
            log.Debug("In RegisterGame");
            GameInfo game = null;
            if (!string.IsNullOrWhiteSpace(label))
            {
                log.DebugFormat("Register {0} at {1}", label, publicEP.ToString());

                game = new GameInfo() { Label = label, CommunicationEndPoint = publicEP, AliveTimestamp = DateTime.Now };
                game.Id = GetNextIdNumber();
                log.DebugFormat("New game's id={0}", game.Id);
                lock (myLock)
                {
                    games.Add(game.Id, game);
                }
                Save();
                LogContents();
            }
            return game;
        }

        public List<GameInfo> GetGames(GameInfo.GameStatus status)
        {
            log.Debug("In GetGames");

            List<GameInfo> filteredGameList = new List<GameInfo>();

            lock (myLock)
            {
                Dictionary<int, GameInfo>.Enumerator enumerator = games.GetEnumerator();
                while (enumerator.MoveNext())
                {
                    if (enumerator.Current.Value.Status == status)
                        filteredGameList.Add(enumerator.Current.Value);
                }
            }
            return filteredGameList;
        }

        public void AmAlive(int gameId)
        {
            lock (myLock)
            {
                if (games.ContainsKey(gameId))
                    games[gameId].AliveTimestamp = DateTime.Now;
            }
        }

        public void ChangeGameStatus(int gameId, GameInfo.GameStatus status)
        {
            log.DebugFormat("In ChangeChangeStatus for gameId={0}", gameId);
            lock (myLock)
            {
                if (games.ContainsKey(gameId))
                {
                    log.DebugFormat("Change status to {0}", status);
                    games[gameId].Status = status;
                    games[gameId].AliveTimestamp = DateTime.Now;
                    Save();
                }
            }
        }

        public void LoadFromFile(string filename)
        {
            log.Debug("In LoadFromFile");
            if (!string.IsNullOrWhiteSpace(filename) &&
                File.Exists(filename))
            {
                log.DebugFormat("Load from {0}", filename);
                this.fileName = filename;
                StreamReader reader = new StreamReader(fileName);
                while (!reader.EndOfStream)
                {
                    string entry = reader.ReadLine();
                    string[] fields = entry.Split(',');
                    if (fields.Length == 4)
                    {
                        Int16 gameId;
                        if (Int16.TryParse(fields[0], out gameId))
                        {
                            GameInfo game = new GameInfo(gameId, fields[1], new Common.EndPoint(fields[2]), fields[3]);
                            game.AliveTimestamp = DateTime.Now;
                            lock (myLock)
                            {
                                if (games.ContainsKey(game.Id))
                                    games[game.Id] = game;
                                else
                                    games.Add(game.Id, game);
                            }
                        }
                    }
                }
                reader.Close();
            }
        }

        public void Save()
        {
            SaveToFile(fileName);
        }

        public void SaveToFile(string filename)
        {
            log.Debug("In SaveToFile");
            if (!string.IsNullOrWhiteSpace(filename))
            {
                log.DebugFormat("Save to {0}", filename);
                this.fileName = filename;
                StreamWriter writer = new StreamWriter(filename);

                lock (myLock)
                {
                    Dictionary<int, GameInfo>.Enumerator iterator = games.GetEnumerator();
                    while (iterator.MoveNext())
                    {
                        GameInfo game = iterator.Current.Value;
                        if ((game.Status != GameInfo.GameStatus.DEAD &&
                            game.Status != GameInfo.GameStatus.COMPLETED) ||
                            iterator.Current.Value.AliveTimestamp.AddMilliseconds(cleanupFrequency) >= DateTime.Now)
                            writer.WriteLine("{0},{1},{2},{3}",
                                                        game.Id,
                                                        game.Label,
                                                        game.CommunicationEndPoint.ToString(),
                                                        (Int16) game.Status);
                    }
                }
                writer.Close();
            }
        }
        #endregion

        #region Private Methods
        private Int16 GetNextIdNumber()
        {
            if (NextGameId == Int16.MaxValue)
                NextGameId = 1;
            return NextGameId++;
        }

        private void Cleanup(object state)
        {
            // Perform a test-and-set operation to check if another thread is already in this method.  Skip,
            // if there is one.
            if (Interlocked.CompareExchange(ref inCleanup, 1, 0) == 0)
            {
                log.Debug("Do a Cleanup");
                Dictionary<int, GameInfo> livingGames = new Dictionary<int, GameInfo>();
                lock (myLock)
                {
                    Dictionary<int, GameInfo>.Enumerator iterator = games.GetEnumerator();
                    while (iterator.MoveNext())
                    {
                        GameInfo game = iterator.Current.Value;
                        bool keep = false;
                        switch (iterator.Current.Value.Status)
                        {
                            case GameInfo.GameStatus.AVAILABLE:
                            case GameInfo.GameStatus.RUNNING:
                            case GameInfo.GameStatus.NOT_INITIAlIZED:
                                keep = true;
                                if (game.AliveTimestamp.AddMilliseconds(deadTimeout) < DateTime.Now)
                                    game.Status = GameInfo.GameStatus.DEAD;
                                break;
                            case GameInfo.GameStatus.DEAD:
                            case GameInfo.GameStatus.COMPLETED:
                                keep = (game.AliveTimestamp.AddMilliseconds(cleanupFrequency) >= DateTime.Now);
                                break;
                        }
                        if (keep)
                            livingGames.Add(game.Id, game);
                    }
                    games = livingGames;
                }
                inCleanup = 0;
                LogContents();
            }
        }

        private void LogContents()
        {
            #if (DEBUG)
            lock (myLock)
            {
                Dictionary<int, GameInfo>.Enumerator iterator = games.GetEnumerator();
                while (iterator.MoveNext())
                {
                    log.DebugFormat("Id={0,-10} EP={1,-20} Label={2,-50} Status={3}", iterator.Current.Value.Id, iterator.Current.Value.CommunicationEndPoint.ToString(),
                                    iterator.Current.Value.Label, iterator.Current.Value.Status.ToString());
                }
            }
            #endif
        }
        #endregion

    }
}