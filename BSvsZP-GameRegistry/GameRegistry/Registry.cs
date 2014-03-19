using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;
using System.Threading;

using Common;

namespace GameRegistry
{
    public class Registry : IDisposable
    {
        #region Private and Protected Data Members
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
            games = new Dictionary<int, GameInfo>();
            cleanupTimer = new Timer(Cleanup, null, cleanupFrequency, cleanupFrequency);
        }

        public void Dispose()
        {
            if (games != null)
                games.Clear();
            if (cleanupTimer != null)
                cleanupTimer.Dispose();
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

        public GameInfo RegisterGame(string label, Common.EndPoint publicEP)
        {
            GameInfo game = null;
            if (!string.IsNullOrWhiteSpace(label))
            {
                game = new GameInfo() { Label = label, CommunicationEndPoint = publicEP, AliveTimestamp = DateTime.Now };
                game.Id = GetNextIdNumber();
                lock (myLock)
                {
                    games.Add(game.Id, game);
                }
                Save();
            }
            return game;
        }

        public List<GameInfo> GetGames(GameInfo.GameStatus status)
        {
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
            lock (myLock)
            {
                if (games.ContainsKey(gameId))
                {
                    games[gameId].Status = status;
                    games[gameId].AliveTimestamp = DateTime.Now;
                    Save();
                }
            }
        }

        public void LoadFromFile(string filename)
        {
            if (!string.IsNullOrWhiteSpace(filename) &&
                File.Exists(filename))
            {
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
            if (!string.IsNullOrWhiteSpace(filename))
            {
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
            }
        }
        #endregion

    }
}