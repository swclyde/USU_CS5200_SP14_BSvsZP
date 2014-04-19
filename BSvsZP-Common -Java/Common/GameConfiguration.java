package Common;

import java.io.NotActiveException;

import org.omg.CORBA.portable.ApplicationException;

import Common.DistributableObject.DISTRIBUTABLE_CLASS_IDS;

public class GameConfiguration extends DistributableObject {


    private short PlayingFieldWidth;
    private short PlayingFieldHeight;

    private short BrilliantStudentRegistrationMin;
    private short BrilliantStudentRegistrationMax;
    private float BrilliantStudentInitialStrength;          // Strength units
    private float BrilliantStudentBaseSpeed;                // Distance units (Squares) per move request (tick)
    private float BrilliantStudentSidewalkSpeedMultiplier;  // Distance units (Squares) per move request (tick)
    private float BrilliantStudentDeathToZombieDelay;       // Number of seconds before a dead student becomes a zombie

    private float ExcuseGeneratorInitialStrength;           // Strength units
    private short ExcuseGeneratorRegistrationMin;
    private short ExcuseGeneratorRegistrationMax;
   
   
    private float WhiningSpinnerInitialStrength;       // Strength units
    private float WhiningTwineCreationRate;                  // Excuses per tick or the inverse of ticks per excuse
   // private float WhiningTwineCreationAcceleration;         // Increase in creation rate per 10 seconds
    private short WhiningSpinnerRegistrationMin;
    private short WhiningSpinnerRegistrationMax;
    
    
    private short ZombieInitialStrengthMin;
    private short ZombieInitialStrengthMax;
    private float ZombieInitialSpeedMax;
    private float ZombieInitialSpeedMin;
    private float ZombieSidewalkSpeedMultiplier;            // Distance units (Squares) per move request (tick)
    private float ZombieCreationRate;                   // # per 10 seconds
    private float ZombieCreationAcceleration;              // increase in creation rate per 10 seconds
    private float ZombieEatingRate;                       // Strength units per ear request (tick)
    private float ZombieStrengthIncreaseForEatingStudent;
    private float ZombieStrengthIncreaseForExcuseGenerator;
    private float ZombieStrengthIncreaseForWhiningSpinner;

    private short BombExcuseDamage;
    private float BombTwinePerSquareOfDistance;            // Number of twine pieces per unit of distance
    private float BombDamageDiffusionFactor;               // Percentage of remaining damage that can be spread to surround squares

    public short RefereeRegistrationMin;
    public short RefereeRegistrationMax;
    
    public short TickInterval;
    private short TickLifetime;                       // Number of seconds that a tick remains active
    private float TicksToStrengthRatio;

    private static int MinimumEncodingLength;
    
    private byte NumberOfTicksRequiredToBuildAnExcuse;
    public byte NumberOfTicksRequiredToBuildTwine;

    public GameConfiguration() {
        ResetToDefaults();
    }

    public GameConfiguration(GameConfiguration orig) {
    	 PlayingFieldWidth = orig.PlayingFieldWidth;
         PlayingFieldHeight = orig.PlayingFieldHeight;

         BrilliantStudentRegistrationMin = orig.BrilliantStudentRegistrationMin;
         BrilliantStudentRegistrationMax = orig.BrilliantStudentRegistrationMax;
         BrilliantStudentInitialStrength = orig.BrilliantStudentInitialStrength;
         BrilliantStudentBaseSpeed = orig.BrilliantStudentBaseSpeed;
         BrilliantStudentSidewalkSpeedMultiplier = orig.BrilliantStudentSidewalkSpeedMultiplier;
         BrilliantStudentDeathToZombieDelay = orig.BrilliantStudentDeathToZombieDelay;

         ExcuseGeneratorInitialStrength = orig.ExcuseGeneratorInitialStrength;
         
         ExcuseGeneratorRegistrationMin = orig.ExcuseGeneratorRegistrationMin;
         ExcuseGeneratorRegistrationMax = orig.ExcuseGeneratorRegistrationMax;
         
         
         WhiningSpinnerInitialStrength = orig.WhiningSpinnerInitialStrength;
         WhiningTwineCreationRate = orig.WhiningTwineCreationRate;
       
         WhiningSpinnerRegistrationMin = orig.WhiningSpinnerRegistrationMin;
         WhiningSpinnerRegistrationMax = orig.WhiningSpinnerRegistrationMax;

         
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
         
         RefereeRegistrationMin = orig.RefereeRegistrationMin;
         RefereeRegistrationMax = orig.RefereeRegistrationMax;
         
         NumberOfTicksRequiredToBuildAnExcuse = orig.NumberOfTicksRequiredToBuildAnExcuse;
         NumberOfTicksRequiredToBuildTwine = orig.NumberOfTicksRequiredToBuildTwine;

    }

    public static GameConfiguration Create(ByteList bytes) throws ApplicationException, Exception {
        GameConfiguration result = new GameConfiguration();
        result.Decode(bytes);
        return result;
    }

    public short getPlayingFieldWidth() {
        return PlayingFieldWidth;
    }

    public short getClassId() {
        return (short) DISTRIBUTABLE_CLASS_IDS.GameConfiguration.getValue();
    }

    public void setPlayingFieldWidth(short playingFieldWidth) {
        PlayingFieldWidth = playingFieldWidth;
    }

    public short getPlayingFieldHeight() {
        return PlayingFieldHeight;
    }

    public void setPlayingFieldHeight(short playingFieldHeight) {
        PlayingFieldHeight = playingFieldHeight;
    }

    public short getBrilliantStudentRegistrationMin() {
        return BrilliantStudentRegistrationMin;
    }

    public void setBrilliantStudentRegistrationMin(
            short brilliantStudentRegistrationMin) {
        BrilliantStudentRegistrationMin = brilliantStudentRegistrationMin;
    }

    public short getBrilliantStudentRegistrationMax() {
        return BrilliantStudentRegistrationMax;
    }

    public void setBrilliantStudentRegistrationMax(
            short brilliantStudentRegistrationMax) {
        BrilliantStudentRegistrationMax = brilliantStudentRegistrationMax;
    }

    public short getExcuseGeneratorRegistrationMin() {
        return ExcuseGeneratorRegistrationMin;
    }

    public void setExcuseGeneratorRegistrationMin(
            short excuseGeneratorRegistrationMin) {
        ExcuseGeneratorRegistrationMin = excuseGeneratorRegistrationMin;
    }

    public short getExcuseGeneratorRegistrationMax() {
        return ExcuseGeneratorRegistrationMax;
    }

    public void setExcuseGeneratorRegistrationMax(
            short excuseGeneratorRegistrationMax) {
        ExcuseGeneratorRegistrationMax = excuseGeneratorRegistrationMax;
    }

    public short getWhiningSpinnerRegistrationMin() {
        return WhiningSpinnerRegistrationMin;
    }

    public void setWhiningSpinnerRegistrationMin(short whiningSpinnerRegistrationMin) {
        WhiningSpinnerRegistrationMin = whiningSpinnerRegistrationMin;
    }

    public short getWhiningSpinnerRegistrationMax() {
        return WhiningSpinnerRegistrationMax;
    }

    public void setWhiningSpinnerRegistrationMax(short whiningSpinnerRegistrationMax) {
        WhiningSpinnerRegistrationMax = whiningSpinnerRegistrationMax;
    }

    public float getBrilliantStudentInitialStrength() {
        return BrilliantStudentInitialStrength;
    }

    public void setBrilliantStudentInitialStrength(
            float brilliantStudentInitialStrength) {
        BrilliantStudentInitialStrength = brilliantStudentInitialStrength;
    }

    public float getBrilliantStudentBaseSpeed() {
        return BrilliantStudentBaseSpeed;
    }

    public void setBrilliantStudentBaseSpeed(float brilliantStudentBaseSpeed) {
        BrilliantStudentBaseSpeed = brilliantStudentBaseSpeed;
    }

    public float getBrilliantStudentSidewalkSpeedMultiplier() {
        return BrilliantStudentSidewalkSpeedMultiplier;
    }

    public void setBrilliantStudentSidewalkSpeedMultiplier(
            float brilliantStudentSidewalkSpeedMultiplier) {
        BrilliantStudentSidewalkSpeedMultiplier = brilliantStudentSidewalkSpeedMultiplier;
    }

    public float getBrilliantStudentDeathToZombieDelay() {
        return BrilliantStudentDeathToZombieDelay;
    }

    public void setBrilliantStudentDeathToZombieDelay(
            float brilliantStudentDeathToZombieDelay) {
        BrilliantStudentDeathToZombieDelay = brilliantStudentDeathToZombieDelay;
    }

    public float getExcuseGeneratorInitialStrength() {
        return ExcuseGeneratorInitialStrength;
    }

    public void setExcuseGeneratorInitialStrength(
            float excuseGeneratorInitialStrength) {
        ExcuseGeneratorInitialStrength = excuseGeneratorInitialStrength;
    }

    
    public float getWhiningSpinnerInitialStrength() {
        return WhiningSpinnerInitialStrength;
    }

    public void setWhiningSpinnerInitialStrength(float whiningSpinnerInitialStrength) {
        WhiningSpinnerInitialStrength = whiningSpinnerInitialStrength;
    }

    public float getWhiningTwineCreationRate() {
        return WhiningTwineCreationRate;
    }

    public void setWhiningTwineCreationRate(float whiningTwineCreationRate) {
        WhiningTwineCreationRate = whiningTwineCreationRate;
    }

   
    public short getZombieInitialStrengthMin() {
        return ZombieInitialStrengthMin;
    }

    public void setZombieInitialStrengthMin(short zombieInitialStrengthMin) {
        ZombieInitialStrengthMin = zombieInitialStrengthMin;
    }

    public short getZombieInitialStrengthMax() {
        return ZombieInitialStrengthMax;
    }

    public void setZombieInitialStrengthMax(short zombieInitialStrengthMax) {
        ZombieInitialStrengthMax = zombieInitialStrengthMax;
    }

    public float getZombieInitialSpeedMax() {
        return ZombieInitialSpeedMax;
    }

    public void setZombieInitialSpeedMax(float zombieInitialSpeedMax) {
        ZombieInitialSpeedMax = zombieInitialSpeedMax;
    }

    public float getZombieInitialSpeedMin() {
        return ZombieInitialSpeedMin;
    }

    public void setZombieInitialSpeedMin(float zombieInitialSpeedMin) {
        ZombieInitialSpeedMin = zombieInitialSpeedMin;
    }

    public float getZombieSidewalkSpeedMultiplier() {
        return ZombieSidewalkSpeedMultiplier;
    }

    public void setZombieSidewalkSpeedMultiplier(float zombieSidewalkSpeedMultiplier) {
        ZombieSidewalkSpeedMultiplier = zombieSidewalkSpeedMultiplier;
    }

    public float getZombieCreationRate() {
        return ZombieCreationRate;
    }

    public void setZombieCreationRate(float zombieCreationRate) {
        ZombieCreationRate = zombieCreationRate;
    }

    public float getZombieCreationAcceleration() {
        return ZombieCreationAcceleration;
    }

    public void setZombieCreationAcceleration(float zombieCreationAcceleration) {
        ZombieCreationAcceleration = zombieCreationAcceleration;
    }

    public float getZombieEatingRate() {
        return ZombieEatingRate;
    }

    public void setZombieEatingRate(float zombieEatingRate) {
        ZombieEatingRate = zombieEatingRate;
    }

    public float getZombieStrengthIncreaseForEatingStudent() {
        return ZombieStrengthIncreaseForEatingStudent;
    }

    public void setZombieStrengthIncreaseForEatingStudent(
            float zombieStrengthIncreaseForEatingStudent) {
        ZombieStrengthIncreaseForEatingStudent = zombieStrengthIncreaseForEatingStudent;
    }

    public float getZombieStrengthIncreaseForExcuseGenerator() {
        return ZombieStrengthIncreaseForExcuseGenerator;
    }

    public void setZombieStrengthIncreaseForExcuseGenerator(
            float zombieStrengthIncreaseForExcuseGenerator) {
        ZombieStrengthIncreaseForExcuseGenerator = zombieStrengthIncreaseForExcuseGenerator;
    }

    public float getZombieStrengthIncreaseForWhiningSpinner() {
        return ZombieStrengthIncreaseForWhiningSpinner;
    }

    public void setZombieStrengthIncreaseForWhiningSpinner(
            float zombieStrengthIncreaseForWhiningSpinner) {
        ZombieStrengthIncreaseForWhiningSpinner = zombieStrengthIncreaseForWhiningSpinner;
    }

    public short getBombExcuseDamage() {
        return BombExcuseDamage;
    }

    public void setBombExcuseDamage(short bombExcuseDamage) {
        BombExcuseDamage = bombExcuseDamage;
    }

    public float getBombTwinePerSquareOfDistance() {
        return BombTwinePerSquareOfDistance;
    }

    public void setBombTwinePerSquareOfDistance(float bombTwinePerSquareOfDistance) {
        BombTwinePerSquareOfDistance = bombTwinePerSquareOfDistance;
    }

    public float getBombDamageDiffusionFactor() {
        return BombDamageDiffusionFactor;
    }

    public void setBombDamageDiffusionFactor(float bombDamageDiffusionFactor) {
        BombDamageDiffusionFactor = bombDamageDiffusionFactor;
    }

    public short getTickLifetime() {
        return TickLifetime;
    }

    public void setTickLifetime(short tickLifetime) {
        TickLifetime = tickLifetime;
    }

    public float getTicksToStrengthRatio() {
        return TicksToStrengthRatio;
    }

    public void setTicksToStrengthRatio(float ticksToStrengthRatio) {
        TicksToStrengthRatio = ticksToStrengthRatio;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
        						+ 2 * 15 // Int16 properties
        						+ 4 * 18;      // float properties
        return MinimumEncodingLength;
    }

    public short getRefereeRegistrationMin() {
		return RefereeRegistrationMin;
	}

	public void setRefereeRegistrationMin(short refereeRegistrationMin) {
		RefereeRegistrationMin = refereeRegistrationMin;
	}

	public short getRefereeRegistrationMax() {
		return RefereeRegistrationMax;
	}

	public void setRefereeRegistrationMax(short refereeRegistrationMax) {
		RefereeRegistrationMax = refereeRegistrationMax;
	}

	public short getTickInterval() {
		return TickInterval;
	}

	public void setTickInterval(short tickInterval) {
		TickInterval = tickInterval;
	}

	public byte getNumberOfTicksRequiredToBuildAnExcuse() {
		return NumberOfTicksRequiredToBuildAnExcuse;
	}

	public void setNumberOfTicksRequiredToBuildAnExcuse(
			byte numberOfTicksRequiredToBuildAnExcuse) {
		NumberOfTicksRequiredToBuildAnExcuse = numberOfTicksRequiredToBuildAnExcuse;
	}

	public byte getNumberOfTicksRequiredToBuildTwine() {
		return NumberOfTicksRequiredToBuildTwine;
	}

	public void setNumberOfTicksRequiredToBuildTwine(
			byte numberOfTicksRequiredToBuildTwine) {
		NumberOfTicksRequiredToBuildTwine = numberOfTicksRequiredToBuildTwine;
	}
    @Override
    public void Encode(ByteList bytes) throws NotActiveException, Exception {
    	bytes.Add((short) DISTRIBUTABLE_CLASS_IDS.GameConfiguration.getValue());                             // Write out the class type

        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later

        bytes.Add((short) 0);                           // Write out a place holder for the length

        bytes.AddObjects(
        		getPlayingFieldWidth(),
                getPlayingFieldHeight(),

                getBrilliantStudentRegistrationMin(),
                getBrilliantStudentRegistrationMax(),
                getBrilliantStudentInitialStrength(),
                getBrilliantStudentBaseSpeed(),
                getBrilliantStudentSidewalkSpeedMultiplier(),
                getBrilliantStudentDeathToZombieDelay(),

                getExcuseGeneratorRegistrationMin(),
                getExcuseGeneratorRegistrationMax(),
                getExcuseGeneratorInitialStrength(),
                getNumberOfTicksRequiredToBuildAnExcuse(),

                getWhiningSpinnerRegistrationMin(),
                getWhiningSpinnerRegistrationMax(),
                getWhiningSpinnerInitialStrength(),
                getNumberOfTicksRequiredToBuildTwine(),

                getZombieInitialStrengthMin(),
                getZombieInitialStrengthMax(),
                getZombieInitialSpeedMax(),
                getZombieInitialSpeedMin(),
                getZombieSidewalkSpeedMultiplier(),
                getZombieCreationRate(),
                getZombieCreationAcceleration(),
                getZombieEatingRate(),
                getZombieStrengthIncreaseForEatingStudent(),
                getZombieStrengthIncreaseForExcuseGenerator(),
                getZombieStrengthIncreaseForWhiningSpinner(),

                getRefereeRegistrationMin(),
                getRefereeRegistrationMax(),

                getBombExcuseDamage(),
                getBombTwinePerSquareOfDistance(),
                getBombDamageDiffusionFactor(),

                getTickInterval(),
                getTickLifetime(),
                getTicksToStrengthRatio());


        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);
				          // Write out the length of this object    

    }


	@Override
    protected void Decode(ByteList bytes) throws ApplicationException, Exception {
		 if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength()) {
	            throw new ApplicationException("Invalid byte array", null);
	        } else if (bytes.PeekInt16() !=  (short) DISTRIBUTABLE_CLASS_IDS.GameConfiguration.getValue()) {
	            throw new ApplicationException("Invalid class id", null);
	        } else {
	        	short objType = bytes.GetInt16();
	            short objLength = bytes.GetInt16();

	            bytes.SetNewReadLimit(objLength);

	            PlayingFieldWidth = bytes.GetInt16();
	            PlayingFieldHeight = bytes.GetInt16();

	            BrilliantStudentRegistrationMin = bytes.GetInt16();
	            BrilliantStudentRegistrationMax = bytes.GetInt16();

	            BrilliantStudentInitialStrength = bytes.GetFloat();
	            BrilliantStudentBaseSpeed = bytes.GetFloat();
	            BrilliantStudentSidewalkSpeedMultiplier = bytes.GetFloat();
	            BrilliantStudentDeathToZombieDelay = bytes.GetFloat();

	            ExcuseGeneratorRegistrationMin = bytes.GetInt16();
	            ExcuseGeneratorRegistrationMax = bytes.GetInt16();
	            ExcuseGeneratorInitialStrength = bytes.GetFloat();
	            NumberOfTicksRequiredToBuildAnExcuse = bytes.GetByte();

	            WhiningSpinnerRegistrationMin = bytes.GetInt16();
	            WhiningSpinnerRegistrationMax = bytes.GetInt16();
	            WhiningSpinnerInitialStrength = bytes.GetFloat();
	            NumberOfTicksRequiredToBuildTwine = bytes.GetByte();

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

	            RefereeRegistrationMin = bytes.GetInt16();
	            RefereeRegistrationMax = bytes.GetInt16();

	            BombExcuseDamage = bytes.GetInt16();
	            BombTwinePerSquareOfDistance = bytes.GetFloat();
	            BombDamageDiffusionFactor = bytes.GetFloat();

	            TickInterval = bytes.GetInt16();
	            TickLifetime = bytes.GetInt16();
	            TicksToStrengthRatio = bytes.GetFloat();

	            bytes.RestorePreviosReadLimit();
	        }
    }
    

    public void ResetToDefaults() {
    	 PlayingFieldWidth = 100;
         PlayingFieldHeight = 100;

         BrilliantStudentRegistrationMin = 10;
         BrilliantStudentRegistrationMax = 20;
         BrilliantStudentInitialStrength = 100.0F;
         BrilliantStudentBaseSpeed = 0.25F;
         BrilliantStudentSidewalkSpeedMultiplier = 1.5F;
         BrilliantStudentDeathToZombieDelay = 2.0F;

         ExcuseGeneratorInitialStrength = 100.0F;
         
         ExcuseGeneratorRegistrationMin = 10;
         ExcuseGeneratorRegistrationMax = 20;
         
         
         WhiningSpinnerInitialStrength = 100.0F;
         WhiningSpinnerRegistrationMin = 10;
         WhiningSpinnerRegistrationMax = 20;
         
         
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

         TickLifetime = 120;
         TicksToStrengthRatio = 1.0F;

         NumberOfTicksRequiredToBuildAnExcuse = 3;
         NumberOfTicksRequiredToBuildTwine = 3;
         
         RefereeRegistrationMin = 0;
         RefereeRegistrationMax = 2;

    }
}
