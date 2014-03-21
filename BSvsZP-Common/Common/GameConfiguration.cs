using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;

namespace Common
{
    public class GameConfiguration : DistributableObject
    {
        #region Private Data Members and Properties
        // Define this class, identifier for serialization / deserialiation purposes
        private static Int16 ClassId { get { return (Int16) DISTRIBUTABLE_CLASS_IDS.GameConfiguration; } }

        #endregion

        #region Public Properties
        [Description("Width of the playing field"), Category("Playing Field")] 
        public Int16 PlayingFieldWidth { get; set; }
        [Description("Height of the playing field"), Category("Playing Field")]
        public Int16 PlayingFieldHeight { get; set; }

        [Description("Minimum Number of Brilliant Students"), Category("Brilliant Students")]
        public Int16 BrilliantStudentRegistrationMin { get; set; }
        [Description("Maximum Number of Brilliant Students"), Category("Brilliant Students")]
        public Int16 BrilliantStudentRegistrationMax { get; set; }
        [Description("Initial Strength for Brilliant Students"), Category("Brilliant Students")]
        public float BrilliantStudentInitialStrength { get; set; }
        [Description("Base Speed (squares per tick) for Brilliant Students"), Category("Brilliant Students")]
        public float BrilliantStudentBaseSpeed { get; set; }
        [Description("Speed Multiplier (squares per tick) for Brilliant Students"), Category("Brilliant Students")]
        public float BrilliantStudentSidewalkSpeedMultiplier { get; set; }
        [Description("Number of seconds before a dead student becomes a zombie"), Category("Brilliant Students")]
        public float BrilliantStudentDeathToZombieDelay { get; set; }

        [Description("Minimum Number of Excuse Generators"), Category("Excuse Generators")]
        public Int16 ExcuseGeneratorRegistrationMin { get; set; }
        [Description("Maximum Number of Excuse Generators"), Category("Excuse Generators")]
        public Int16 ExcuseGeneratorRegistrationMax { get; set; }
        [Description("Initial Strength for Excuse Generators"), Category("Excuse Generators")]
        public float ExcuseGeneratorInitialStrength { get; set; }
        [Description("Excuses per tick ratio (must be less than 1)"), Category("Excuse Generators")]
        public float ExcuseCreationRate { get; set; }
        [Description("Increate to excuse creation rate every 10 seconds"), Category("Excuse Generators")]
        public float ExcuseCreationAcceleration { get; set; }

        [Description("Minimum Number of Whining Spinners"), Category("Whining Spinners")]
        public Int16 WhiningSpinnerRegistrationMin { get; set; }
        [Description("Maximum Number of Whining Spinners"), Category("Whining Spinners")]
        public Int16 WhiningSpinnerRegistrationMax { get; set; }
        [Description("Initial Strength for Whining Spinners"), Category("Whining Spinners")]
        public float WhiningSpinnerInitialStrength { get; set; }
        [Description("Whining twine per tick ratio (must be less than 1)"), Category("Whining Spinners")]
        public float WhiningTwineCreationRate { get; set; }
        [Description("Increate to excuse creation rate every 10 seconds"), Category("Whining Spinners")]
        public float WhiningTwineCreationAcceleration { get; set; }

        [Description("Minimum bound for a zombie's initial strength"), Category("Zombies")]
        public Int16 ZombieInitialStrengthMin { get; set; }
        [Description("Maximum bound for a zombie's initial strength"), Category("Zombies")]
        public Int16 ZombieInitialStrengthMax { get; set; }
        [Description("Minimum bound for a zombie's initial speed"), Category("Zombies")]
        public float ZombieInitialSpeedMin { get; set; }
        [Description("Maximum bound for a zombie's initial speed"), Category("Zombies")]
        public float ZombieInitialSpeedMax { get; set; }
        [Description("Multiplier for a Zombie's speed on the sidewalk"), Category("Zombies")]
        public float ZombieSidewalkSpeedMultiplier { get; set; }            // Distance units (Squares) per move request (tick)
        [Description("Initial Zombie creation rate (number of zombies per 10 seconds"), Category("Zombies")]
        public float ZombieCreationRate { get; set; }                       // # per 10 seconds
        [Description("Increase to Zombie creation rate every 10 seconds"), Category("Zombies")]
        public float ZombieCreationAcceleration { get; set; }               // increase in creation rate per 10 seconds
        [Description("Zombie eating rate (strength units per tick)"), Category("Zombies")]
        public float ZombieEatingRate { get; set; }                         // Strength units per ear request (tick)
        [Description("Increase to a Zombie's strength for eating a Briliant Student"), Category("Zombies")]
        public float ZombieStrengthIncreaseForEatingStudent { get; set; }
        [Description("Increase to a Zombie's strength for eating an Excuse Generator"), Category("Zombies")]
        public float ZombieStrengthIncreaseForExcuseGenerator { get; set; }
        [Description("Increase to a Zombie's strength for eating an Whinning Spinner"), Category("Zombies")]
        public float ZombieStrengthIncreaseForWhiningSpinner { get; set; }

        [Description("Amount of damange each excuse in a Bomb has on a Zombie"), Category("Bombs")]
        public Int16 BombExcuseDamage { get; set; }
        [Description("Number of twine pieces per unit of distance"), Category("Bombs")]
        public float BombTwinePerSquareOfDistance { get; set; }
        [Description("Percentage of remaining damage that can be spread to surround squares"), Category("Bombs")]
        public float BombDamageDiffusionFactor { get; set; }

        [Description("Delay in milliseconds between ticks"), Category("Ticks")]
        public Int16 TickInterval { get; set; }
        [Description("Number of seconds that a tick remains active"), Category("Ticks")]
        public Int16 TickLifetime { get; set; }
        [Description("Ticks to Strength Ratio"), Category("Ticks")]
        public float TicksToStrengthRatio { get; set; }

        public static int MinimumEncodingLength
        {
            get
            {
                return 4              // Object header
                       + 2 * 11       // Int16 properties
                       + 4 * 21;      // float properties
            }
        }
        #endregion

        #region Constructors
        public GameConfiguration() { ResetToDefaults(); }
        public GameConfiguration(GameConfiguration orig)
        {
            PlayingFieldWidth = orig.PlayingFieldWidth;
            PlayingFieldHeight = orig.PlayingFieldHeight;

            BrilliantStudentRegistrationMin = orig.BrilliantStudentRegistrationMin;
            BrilliantStudentRegistrationMax = orig.BrilliantStudentRegistrationMax;
            ExcuseGeneratorRegistrationMin = orig.ExcuseGeneratorRegistrationMin;
            ExcuseGeneratorRegistrationMax = orig.ExcuseGeneratorRegistrationMax;
            WhiningSpinnerRegistrationMin = orig.WhiningSpinnerRegistrationMin;
            WhiningSpinnerRegistrationMax = orig.WhiningSpinnerRegistrationMax;

            BrilliantStudentInitialStrength = orig.BrilliantStudentInitialStrength;
            BrilliantStudentBaseSpeed = orig.BrilliantStudentBaseSpeed;
            BrilliantStudentSidewalkSpeedMultiplier = orig.BrilliantStudentSidewalkSpeedMultiplier;
            BrilliantStudentDeathToZombieDelay = orig.BrilliantStudentDeathToZombieDelay;

            ExcuseGeneratorInitialStrength = orig.ExcuseGeneratorInitialStrength;
            ExcuseCreationRate = orig.ExcuseCreationRate;
            ExcuseCreationAcceleration = orig.ExcuseCreationAcceleration;

            WhiningSpinnerInitialStrength = orig.WhiningSpinnerInitialStrength;
            WhiningTwineCreationRate = orig.WhiningTwineCreationRate;
            WhiningTwineCreationAcceleration = orig.WhiningTwineCreationAcceleration;

            ZombieInitialStrengthMin = orig.ZombieInitialStrengthMin;
            ZombieInitialStrengthMax = orig.ZombieInitialStrengthMax;
            ZombieInitialSpeedMax = orig.ZombieInitialSpeedMax;
            ZombieInitialSpeedMin = orig.ZombieInitialSpeedMin;
            ZombieSidewalkSpeedMultiplier = orig.ZombieSidewalkSpeedMultiplier;
            ZombieCreationRate = orig.ZombieCreationRate;
            ZombieCreationAcceleration = orig.ZombieCreationAcceleration;
            ZombieEatingRate = orig.ZombieEatingRate;
            ZombieStrengthIncreaseForEatingStudent = orig.ZombieStrengthIncreaseForEatingStudent;
            ZombieStrengthIncreaseForExcuseGenerator = orig.ZombieStrengthIncreaseForExcuseGenerator;
            ZombieStrengthIncreaseForWhiningSpinner = orig.ZombieStrengthIncreaseForWhiningSpinner;

            BombExcuseDamage = orig.BombExcuseDamage;
            BombTwinePerSquareOfDistance = orig.BombTwinePerSquareOfDistance;
            BombDamageDiffusionFactor = orig.BombDamageDiffusionFactor;

            TickInterval = orig.TickInterval;
            TickLifetime = orig.TickLifetime;
            TicksToStrengthRatio = orig.TicksToStrengthRatio;

        }
        
        /// <summary>
        /// Factor method to create a FieldLocation from a byte list
        /// </summary>
        /// <param name="bytes">A byte list from which the distributable object will be decoded</param>
        /// <returns>A new object of this class</returns>
        new public static GameConfiguration Create(ByteList bytes)
        {
            GameConfiguration result = new GameConfiguration();
            result.Decode(bytes);
            return result;
        }

        #endregion

        #region Encoding and Decoding methods

        /// <summary>
        /// This method encodes an object of this class into a byte list
        /// </summary>
        /// <param name="bytes"></param>
        public override void Encode(ByteList bytes)
        {
            bytes.Add(ClassId);                             // Write out the class type

            Int16 lengthPos = bytes.CurrentWritePosition;   // Get the current write position, so we
                                                            // can write the length here later

            bytes.Add((Int16) 0);                           // Write out a place holder for the length

            bytes.AddObjects(
                            PlayingFieldWidth,
                            PlayingFieldHeight,

                            BrilliantStudentRegistrationMin,
                            BrilliantStudentRegistrationMax,
                            ExcuseGeneratorRegistrationMin,
                            ExcuseGeneratorRegistrationMax,
                            WhiningSpinnerRegistrationMin,
                            WhiningSpinnerRegistrationMax,

                            BrilliantStudentInitialStrength,
                            BrilliantStudentBaseSpeed,
                            BrilliantStudentSidewalkSpeedMultiplier,
                            BrilliantStudentDeathToZombieDelay,

                            ExcuseGeneratorInitialStrength,
                            ExcuseCreationRate,
                            ExcuseCreationAcceleration,

                            WhiningSpinnerInitialStrength,
                            WhiningTwineCreationRate,
                            WhiningTwineCreationAcceleration,

                            ZombieInitialStrengthMin,
                            ZombieInitialStrengthMax,
                            ZombieInitialSpeedMax,
                            ZombieInitialSpeedMin,
                            ZombieSidewalkSpeedMultiplier,
                            ZombieCreationRate,
                            ZombieCreationAcceleration,
                            ZombieEatingRate,
                            ZombieStrengthIncreaseForEatingStudent,
                            ZombieStrengthIncreaseForExcuseGenerator,
                            ZombieStrengthIncreaseForWhiningSpinner,

                            BombExcuseDamage,
                            BombTwinePerSquareOfDistance,
                            BombDamageDiffusionFactor,

                            TickInterval,
                            TickLifetime,
                            TicksToStrengthRatio);

            Int16 length = Convert.ToInt16(bytes.CurrentWritePosition - lengthPos - 2);
            bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        
        }

        /// <summary>
        /// This method decodes of this classes from a byte list.  It can onlt be called from within the class hierarchy.
        /// </summary>
        /// <param name="messageBytes"></param>
        protected override void Decode(ByteList bytes)
        {
            if (bytes == null || bytes.RemainingToRead < MinimumEncodingLength)
                throw new ApplicationException("Invalid byte array");
            else if (bytes.PeekInt16() != ClassId)
                throw new ApplicationException("Invalid class id");
            else
            {
                Int16 objType = bytes.GetInt16();
                Int16 objLength = bytes.GetInt16();

                bytes.SetNewReadLimit(objLength);

                PlayingFieldWidth = bytes.GetInt16();
                PlayingFieldHeight = bytes.GetInt16();

                BrilliantStudentRegistrationMin = bytes.GetInt16();
                BrilliantStudentRegistrationMax = bytes.GetInt16();
                ExcuseGeneratorRegistrationMin = bytes.GetInt16();
                ExcuseGeneratorRegistrationMax = bytes.GetInt16();
                WhiningSpinnerRegistrationMin = bytes.GetInt16();
                WhiningSpinnerRegistrationMax = bytes.GetInt16();

                BrilliantStudentInitialStrength = bytes.GetFloat();
                BrilliantStudentBaseSpeed = bytes.GetFloat();
                BrilliantStudentSidewalkSpeedMultiplier = bytes.GetFloat();
                BrilliantStudentDeathToZombieDelay = bytes.GetFloat();

                ExcuseGeneratorInitialStrength = bytes.GetFloat();
                ExcuseCreationRate = bytes.GetFloat();
                ExcuseCreationAcceleration = bytes.GetFloat();

                WhiningSpinnerInitialStrength = bytes.GetFloat();
                WhiningTwineCreationRate = bytes.GetFloat();
                WhiningTwineCreationAcceleration = bytes.GetFloat();

                ZombieInitialStrengthMin = bytes.GetInt16();
                ZombieInitialStrengthMax = bytes.GetInt16();
                ZombieInitialSpeedMax = bytes.GetFloat();
                ZombieInitialSpeedMin = bytes.GetFloat();
                ZombieSidewalkSpeedMultiplier  = bytes.GetFloat();
                ZombieCreationRate  = bytes.GetFloat();
                ZombieCreationAcceleration  = bytes.GetFloat();
                ZombieEatingRate = bytes.GetFloat();
                ZombieStrengthIncreaseForEatingStudent = bytes.GetFloat();
                ZombieStrengthIncreaseForExcuseGenerator = bytes.GetFloat();
                ZombieStrengthIncreaseForWhiningSpinner = bytes.GetFloat();

                BombExcuseDamage = bytes.GetInt16();
                BombTwinePerSquareOfDistance = bytes.GetFloat();
                BombDamageDiffusionFactor = bytes.GetFloat();

                TickInterval = bytes.GetInt16();
                TickLifetime = bytes.GetInt16();
                TicksToStrengthRatio = bytes.GetFloat();

                bytes.RestorePreviosReadLimit();
            }
        }

        public void ResetToDefaults()
        {
            PlayingFieldWidth = 100;
            PlayingFieldHeight = 100;

            BrilliantStudentRegistrationMin = 10;
            BrilliantStudentRegistrationMax = 20;
            ExcuseGeneratorRegistrationMin = 10;
            ExcuseGeneratorRegistrationMax = 25;
            WhiningSpinnerRegistrationMin = 10;
            WhiningSpinnerRegistrationMax = 25;

            BrilliantStudentInitialStrength = 100.0F;
            BrilliantStudentBaseSpeed = .25F;
            BrilliantStudentSidewalkSpeedMultiplier = 1.5F;
            BrilliantStudentDeathToZombieDelay = 2.0F;

            ExcuseGeneratorInitialStrength = 100.0F;
            ExcuseCreationRate = 0.25F;
            ExcuseCreationAcceleration = 0.125F;

            WhiningSpinnerInitialStrength = 100.0F;
            WhiningTwineCreationRate = 0.25F;
            WhiningTwineCreationAcceleration = 0.125F;

            ZombieInitialStrengthMin = 25;
            ZombieInitialStrengthMax = 75;
            ZombieInitialSpeedMax = 0.15F;
            ZombieInitialSpeedMin = 0.5F;
            ZombieSidewalkSpeedMultiplier = 1.5F;
            ZombieCreationRate = 5.0F;
            ZombieCreationAcceleration = 0.5F;
            ZombieEatingRate = 2.0F;
            ZombieStrengthIncreaseForEatingStudent = 10.0F;
            ZombieStrengthIncreaseForExcuseGenerator = 5.0F;
            ZombieStrengthIncreaseForWhiningSpinner = 5.0F;

            BombExcuseDamage = 2;
            BombTwinePerSquareOfDistance = 2.0F;
            BombDamageDiffusionFactor = 0.75F;

            TickInterval = 200;
            TickLifetime = 120;
            TicksToStrengthRatio = 1.0F;

        }

        #endregion

    }
}
