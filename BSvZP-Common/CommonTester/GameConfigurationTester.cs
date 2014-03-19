using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;

namespace CommonTester
{
    [TestClass]
    public class GameConfigurationTester
    {
        [TestMethod]
        public void GameConfiguration_CheckConstructors()
        {
            GameConfiguration gc = new GameConfiguration();
            Assert.AreEqual(100, gc.PlayingFieldWidth);
            Assert.AreEqual(100, gc.PlayingFieldHeight);

            Assert.AreEqual(10, gc.BrilliantStudentRegistrationMin);
            Assert.AreEqual(20, gc.BrilliantStudentRegistrationMax);
            Assert.AreEqual(10, gc.ExcuseGeneratorRegistrationMin);
            Assert.AreEqual(25, gc.ExcuseGeneratorRegistrationMax);
            Assert.AreEqual(10, gc.WhiningSpinnerRegistrationMin);
            Assert.AreEqual(25, gc.WhiningSpinnerRegistrationMax);

            Assert.AreEqual(100.0F, gc.BrilliantStudentInitialStrength);
            Assert.AreEqual(0.25F, gc.BrilliantStudentBaseSpeed);
            Assert.AreEqual(1.5F, gc.BrilliantStudentSidewalkSpeedMultiplier);
            Assert.AreEqual(2.0F, gc.BrilliantStudentDeathToZombieDelay);

            Assert.AreEqual(100.0F, gc.ExcuseGeneratorInitialStrength);
            Assert.AreEqual(0.25F, gc.ExcuseCreationRate);
            Assert.AreEqual(0.125F, gc.ExcuseCreationAcceleration);

            Assert.AreEqual(100.0F, gc.WhiningSpinnerInitialStrength);
            Assert.AreEqual(0.25F, gc.WhiningTwineCreationRate);
            Assert.AreEqual(0.125F, gc.WhiningTwineCreationAcceleration);

            Assert.AreEqual(25, gc.ZombieInitialStrengthMin);
            Assert.AreEqual(75, gc.ZombieInitialStrengthMax);
            Assert.AreEqual(0.15F, gc.ZombieInitialSpeedMax);
            Assert.AreEqual(0.5F, gc.ZombieInitialSpeedMin);
            Assert.AreEqual(1.5F, gc.ZombieSidewalkSpeedMultiplier);
            Assert.AreEqual(5.0F, gc.ZombieCreationRate);
            Assert.AreEqual(0.5F, gc.ZombieCreationAcceleration);
            Assert.AreEqual(2.0F, gc.ZombieEatingRate);
            Assert.AreEqual(10.0F, gc.ZombieStrengthIncreaseForEatingStudent);
            Assert.AreEqual(5.0F, gc.ZombieStrengthIncreaseForExcuseGenerator);
            Assert.AreEqual(5.0F, gc.ZombieStrengthIncreaseForWhiningSpinner);

            Assert.AreEqual(2, gc.BombExcuseDamage);
            Assert.AreEqual(2.0F, gc.BombTwinePerSquareOfDistance);
            Assert.AreEqual(.75F, gc.BombDamageDiffusionFactor);

            Assert.AreEqual(120, gc.TickLifetime);
            Assert.AreEqual(1.0F, gc.TicksToStrengthRatio);

        }

        [TestMethod]
        public void GameConfiguration_CheckProperties()
        {
            GameConfiguration gc = new GameConfiguration();

            gc.PlayingFieldWidth=100;
            Assert.AreEqual(100, gc.PlayingFieldWidth);
            gc.PlayingFieldHeight=101;
            Assert.AreEqual(101, gc.PlayingFieldHeight);

            gc.BrilliantStudentRegistrationMin=10;
            Assert.AreEqual(10, gc.BrilliantStudentRegistrationMin);
            gc.BrilliantStudentRegistrationMax = 11;
            Assert.AreEqual(11, gc.BrilliantStudentRegistrationMax);
            gc.ExcuseGeneratorRegistrationMin = 12;
            Assert.AreEqual(12, gc.ExcuseGeneratorRegistrationMin);
            gc.ExcuseGeneratorRegistrationMax = 13;
            Assert.AreEqual(13, gc.ExcuseGeneratorRegistrationMax);
            gc.WhiningSpinnerRegistrationMin = 14;
            Assert.AreEqual(14, gc.WhiningSpinnerRegistrationMin);
            gc.WhiningSpinnerRegistrationMax = 15;
            Assert.AreEqual(15, gc.WhiningSpinnerRegistrationMax);

            gc.BrilliantStudentInitialStrength=2.1F;
            Assert.AreEqual(2.1F, gc.BrilliantStudentInitialStrength);
            gc.BrilliantStudentBaseSpeed = 2.2F;
            Assert.AreEqual(2.2F, gc.BrilliantStudentBaseSpeed);
            gc.BrilliantStudentSidewalkSpeedMultiplier = 2.3F;
            Assert.AreEqual(2.3F, gc.BrilliantStudentSidewalkSpeedMultiplier);
            gc.BrilliantStudentDeathToZombieDelay = 2.4F;
            Assert.AreEqual(2.4F, gc.BrilliantStudentDeathToZombieDelay);

            gc.ExcuseGeneratorInitialStrength=2.5F;
            Assert.AreEqual(2.5F, gc.ExcuseGeneratorInitialStrength);
            gc.ExcuseCreationRate = 2.6F;
            Assert.AreEqual(2.6F, gc.ExcuseCreationRate);
            gc.ExcuseCreationAcceleration = 2.7F;
            Assert.AreEqual(2.7F, gc.ExcuseCreationAcceleration);

            gc.WhiningSpinnerInitialStrength=2.8F;
            Assert.AreEqual(2.8F, gc.WhiningSpinnerInitialStrength);
            gc.WhiningTwineCreationRate = 2.9F;
            Assert.AreEqual(2.9F, gc.WhiningTwineCreationRate);
            gc.WhiningTwineCreationAcceleration = 3.0F;
            Assert.AreEqual(3.0F, gc.WhiningTwineCreationAcceleration);

            gc.ZombieInitialStrengthMin=16;
            Assert.AreEqual(16, gc.ZombieInitialStrengthMin);
            gc.ZombieInitialStrengthMax = 17;
            Assert.AreEqual(17, gc.ZombieInitialStrengthMax);
            gc.ZombieInitialSpeedMax = 3.1F;
            Assert.AreEqual(3.1F, gc.ZombieInitialSpeedMax);
            gc.ZombieInitialSpeedMin = 3.2F;
            Assert.AreEqual(3.2F, gc.ZombieInitialSpeedMin);
            gc.ZombieSidewalkSpeedMultiplier=3.3F;
            Assert.AreEqual(3.3F, gc.ZombieSidewalkSpeedMultiplier);
            gc.ZombieCreationRate = 3.4F;
            Assert.AreEqual(3.4F, gc.ZombieCreationRate);
            gc.ZombieCreationAcceleration = 3.5F;
            Assert.AreEqual(3.5F, gc.ZombieCreationAcceleration);
            gc.ZombieEatingRate = 3.6F;
            Assert.AreEqual(3.6F, gc.ZombieEatingRate);
            gc.ZombieStrengthIncreaseForEatingStudent = 3.7F;
            Assert.AreEqual(3.7F, gc.ZombieStrengthIncreaseForEatingStudent);
            gc.ZombieStrengthIncreaseForExcuseGenerator = 3.8F;
            Assert.AreEqual(3.8F, gc.ZombieStrengthIncreaseForExcuseGenerator);
            gc.ZombieStrengthIncreaseForWhiningSpinner = 3.9F;
            Assert.AreEqual(3.9F, gc.ZombieStrengthIncreaseForWhiningSpinner);

            gc.BombExcuseDamage=18;
            Assert.AreEqual(18, gc.BombExcuseDamage);
            gc.BombTwinePerSquareOfDistance = 4.0F;
            Assert.AreEqual(4.0F, gc.BombTwinePerSquareOfDistance);
            gc.BombDamageDiffusionFactor = 4.1F;
            Assert.AreEqual(4.1F, gc.BombDamageDiffusionFactor);

            gc.TickLifetime=19;
            Assert.AreEqual(19, gc.TickLifetime);
            gc.TicksToStrengthRatio = 4.2F;
            Assert.AreEqual(4.2F, gc.TicksToStrengthRatio);

        }

        [TestMethod]
        public void GameConfiguration_CheckEncodeAndDecode()
        {
            GameConfiguration gc1 = new GameConfiguration();

            gc1.PlayingFieldWidth = 50;
            gc1.PlayingFieldHeight = 51;

            gc1.BrilliantStudentRegistrationMin = 10;
            gc1.BrilliantStudentRegistrationMax = 11;
            gc1.ExcuseGeneratorRegistrationMin = 12;
            gc1.ExcuseGeneratorRegistrationMax = 13;
            gc1.WhiningSpinnerRegistrationMin = 14;
            gc1.WhiningSpinnerRegistrationMax = 15;

            gc1.BrilliantStudentInitialStrength = 2.1F;
            gc1.BrilliantStudentBaseSpeed = 2.2F;
            gc1.BrilliantStudentSidewalkSpeedMultiplier = 2.3F;
            gc1.BrilliantStudentDeathToZombieDelay = 2.4F;

            gc1.ExcuseGeneratorInitialStrength = 2.5F;
            gc1.ExcuseCreationRate = 2.6F;
            gc1.ExcuseCreationAcceleration = 2.7F;

            gc1.WhiningSpinnerInitialStrength = 2.8F;
            gc1.WhiningTwineCreationRate = 2.9F;
            gc1.WhiningTwineCreationAcceleration = 3.0F;

            gc1.ZombieInitialStrengthMin = 16;
            gc1.ZombieInitialStrengthMax = 17;
            gc1.ZombieInitialSpeedMax = 3.1F;
            gc1.ZombieInitialSpeedMin = 3.2F;
            gc1.ZombieSidewalkSpeedMultiplier = 3.3F;
            gc1.ZombieCreationRate = 3.4F;
            gc1.ZombieCreationAcceleration = 3.5F;
            gc1.ZombieEatingRate = 3.6F;
            gc1.ZombieStrengthIncreaseForEatingStudent = 3.7F;
            gc1.ZombieStrengthIncreaseForExcuseGenerator = 3.8F;
            gc1.ZombieStrengthIncreaseForWhiningSpinner = 3.9F;

            gc1.BombExcuseDamage = 18;
            gc1.BombTwinePerSquareOfDistance = 4.0F;
            gc1.BombDamageDiffusionFactor = 4.1F;

            gc1.TickLifetime = 19;
            gc1.TicksToStrengthRatio = 4.2F;

            ByteList bytes = new ByteList();
            gc1.Encode(bytes);
            GameConfiguration gc2 = GameConfiguration.Create(bytes);
            Assert.AreEqual(gc1.PlayingFieldWidth, gc2.PlayingFieldWidth);
            Assert.AreEqual(gc1.PlayingFieldHeight, gc2.PlayingFieldHeight);

            Assert.AreEqual(gc1.BrilliantStudentRegistrationMin, gc2.BrilliantStudentRegistrationMin);
            Assert.AreEqual(gc1.BrilliantStudentRegistrationMax, gc2.BrilliantStudentRegistrationMax);
            Assert.AreEqual(gc1.ExcuseGeneratorRegistrationMin, gc2.ExcuseGeneratorRegistrationMin);
            Assert.AreEqual(gc1.ExcuseGeneratorRegistrationMax, gc2.ExcuseGeneratorRegistrationMax);
            Assert.AreEqual(gc1.WhiningSpinnerRegistrationMin, gc2.WhiningSpinnerRegistrationMin);
            Assert.AreEqual(gc1.WhiningSpinnerRegistrationMax, gc2.WhiningSpinnerRegistrationMax);

            Assert.AreEqual(gc1.BrilliantStudentInitialStrength, gc2.BrilliantStudentInitialStrength);
            Assert.AreEqual(gc1.BrilliantStudentBaseSpeed, gc2.BrilliantStudentBaseSpeed);
            Assert.AreEqual(gc1.BrilliantStudentSidewalkSpeedMultiplier, gc2.BrilliantStudentSidewalkSpeedMultiplier);
            Assert.AreEqual(gc1.BrilliantStudentDeathToZombieDelay, gc2.BrilliantStudentDeathToZombieDelay);

            Assert.AreEqual(gc1.ExcuseGeneratorInitialStrength, gc2.ExcuseGeneratorInitialStrength);
            Assert.AreEqual(gc1.ExcuseCreationRate, gc2.ExcuseCreationRate);
            Assert.AreEqual(gc1.ExcuseCreationAcceleration, gc2.ExcuseCreationAcceleration);

            Assert.AreEqual(gc1.WhiningSpinnerInitialStrength, gc2.WhiningSpinnerInitialStrength);
            Assert.AreEqual(gc1.WhiningTwineCreationRate, gc2.WhiningTwineCreationRate);
            Assert.AreEqual(gc1.WhiningTwineCreationAcceleration, gc2.WhiningTwineCreationAcceleration);

            Assert.AreEqual(gc1.ZombieInitialStrengthMin, gc2.ZombieInitialStrengthMin);
            Assert.AreEqual(gc1.ZombieInitialStrengthMax, gc2.ZombieInitialStrengthMax);
            Assert.AreEqual(gc1.ZombieInitialSpeedMax, gc2.ZombieInitialSpeedMax);
            Assert.AreEqual(gc1.ZombieInitialSpeedMin, gc2.ZombieInitialSpeedMin);
            Assert.AreEqual(gc1.ZombieSidewalkSpeedMultiplier, gc2.ZombieSidewalkSpeedMultiplier);
            Assert.AreEqual(gc1.ZombieCreationRate, gc2.ZombieCreationRate);
            Assert.AreEqual(gc1.ZombieCreationAcceleration, gc2.ZombieCreationAcceleration);
            Assert.AreEqual(gc1.ZombieEatingRate, gc2.ZombieEatingRate);
            Assert.AreEqual(gc1.ZombieStrengthIncreaseForEatingStudent, gc2.ZombieStrengthIncreaseForEatingStudent);
            Assert.AreEqual(gc1.ZombieStrengthIncreaseForExcuseGenerator, gc2.ZombieStrengthIncreaseForExcuseGenerator);
            Assert.AreEqual(gc1.ZombieStrengthIncreaseForWhiningSpinner, gc2.ZombieStrengthIncreaseForWhiningSpinner);

            Assert.AreEqual(gc1.BombExcuseDamage, gc2.BombExcuseDamage);
            Assert.AreEqual(gc1.BombTwinePerSquareOfDistance, gc2.BombTwinePerSquareOfDistance);
            Assert.AreEqual(gc1.BombDamageDiffusionFactor, gc2.BombDamageDiffusionFactor);

            Assert.AreEqual(gc1.TickLifetime, gc2.TickLifetime);
            Assert.AreEqual(gc1.TicksToStrengthRatio, gc2.TicksToStrengthRatio);


            bytes.Clear();
            gc1.Encode(bytes);
            bytes.GetByte();            // Read one byte, which will throw the length off
            try
            {
                gc2 = GameConfiguration.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

            bytes.Clear();
            gc1.Encode(bytes);
            bytes.Add((byte)100);       // Add a byte
            bytes.GetByte();            // Read one byte, which will make the ID wrong
            try
            {
                gc2 = GameConfiguration.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

        }
    }
}
