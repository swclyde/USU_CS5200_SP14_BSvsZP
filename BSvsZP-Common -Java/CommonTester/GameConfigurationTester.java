package CommonTester;

import static org.junit.Assert.*;
import java.io.NotActiveException;
import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;
import Common.ByteList;
import Common.GameConfiguration;

public class GameConfigurationTester {

	@Test
	 public void GameConfiguration_CheckConstructors()
    {
        GameConfiguration gc = new GameConfiguration();
        assertEquals(100, gc.getPlayingFieldWidth());
        assertEquals(100, gc.getPlayingFieldHeight());

        assertEquals(10, gc.getBrilliantStudentRegistrationMin());
        assertEquals(20, gc.getBrilliantStudentRegistrationMax());
        assertEquals(10, gc.getExcuseGeneratorRegistrationMin());
        assertEquals(20, gc.getExcuseGeneratorRegistrationMax());
        assertEquals(10, gc.getWhiningSpinnerRegistrationMin());
        assertEquals(20, gc.getWhiningSpinnerRegistrationMax());
       
        assertEquals(100.0F, gc.getBrilliantStudentInitialStrength(), 0.02);
        assertEquals(0.25F, gc.getBrilliantStudentBaseSpeed(), 0.02);
        assertEquals(1.5F, gc.getBrilliantStudentSidewalkSpeedMultiplier(), 0.02);
        assertEquals(2.0F, gc.getBrilliantStudentDeathToZombieDelay(), 0.02);

        assertEquals(100.0F, gc.getExcuseGeneratorInitialStrength(), 0.02);
      

        assertEquals(100.0F, gc.getWhiningSpinnerInitialStrength(), 0.02);
        


        assertEquals(25, gc.getZombieInitialStrengthMin(), 0.02);
        assertEquals(75, gc.getZombieInitialStrengthMax(), 0.02);
        assertEquals(0.15F, gc.getZombieInitialSpeedMax(), 0.02);
        assertEquals(0.5F, gc.getZombieInitialSpeedMin(), 0.02);
        assertEquals(1.5F, gc.getZombieSidewalkSpeedMultiplier(), 0.02);
        assertEquals(5.0F, gc.getZombieCreationRate(), 0.02);
        assertEquals(0.5F, gc.getZombieCreationAcceleration(), 0.02);
        assertEquals(2.0F, gc.getZombieEatingRate(), 0.02);
        assertEquals(10.0F, gc.getZombieStrengthIncreaseForEatingStudent(), 0.02);
        assertEquals(5.0F, gc.getZombieStrengthIncreaseForExcuseGenerator(), 0.02);
        assertEquals(5.0F, gc.getZombieStrengthIncreaseForWhiningSpinner(), 0.02);

        assertEquals(2, gc.getBombExcuseDamage(), 0.02);
        assertEquals(2.0F, gc.getBombTwinePerSquareOfDistance(), 0.02);
        assertEquals(.75F, gc.getBombDamageDiffusionFactor(), 0.02);

        assertEquals(120, gc.getTickLifetime(), 0.02);
        assertEquals(1.0F, gc.getTicksToStrengthRatio(), 0.02);

    }

	@Test
	public void GameConfiguration_CheckProperties()
    {
        GameConfiguration gc = new GameConfiguration();

        gc.setPlayingFieldWidth((short) 100);
        assertEquals(100, gc.getPlayingFieldWidth());
        gc.setPlayingFieldHeight((short) 101);
        assertEquals(101, gc.getPlayingFieldHeight());

        gc.setBrilliantStudentRegistrationMin((short) 10);
        assertEquals(10, gc.getBrilliantStudentRegistrationMin());
        gc.setBrilliantStudentRegistrationMax((short) 11);
        assertEquals(11, gc.getBrilliantStudentRegistrationMax());
        gc.setExcuseGeneratorRegistrationMin((short) 12);
        assertEquals(12, gc.getExcuseGeneratorRegistrationMin());
        gc.setExcuseGeneratorRegistrationMax((short) 13);
        assertEquals(13, gc.getExcuseGeneratorRegistrationMax());
        gc.setWhiningSpinnerRegistrationMin((short) 14);
        assertEquals(14, gc.getWhiningSpinnerRegistrationMin());
        gc.setWhiningSpinnerRegistrationMax((short) 15);
        assertEquals(15, gc.getWhiningSpinnerRegistrationMax());
    
        gc.setBrilliantStudentInitialStrength(2.1F);
        assertEquals(2.1F, gc.getBrilliantStudentInitialStrength(), 0.02);
        gc.setBrilliantStudentBaseSpeed(2.2F);
        assertEquals(2.2F, gc.getBrilliantStudentBaseSpeed(), 0.02);
        gc.setBrilliantStudentSidewalkSpeedMultiplier(2.3F);
        assertEquals(2.3F, gc.getBrilliantStudentSidewalkSpeedMultiplier(), 0.02);
        gc.setBrilliantStudentDeathToZombieDelay(2.4F);
        assertEquals(2.4F, gc.getBrilliantStudentDeathToZombieDelay(), 0.02);

        gc.setExcuseGeneratorInitialStrength(2.5F);
        assertEquals(2.5F, gc.getExcuseGeneratorInitialStrength(), 0.02);
       

        gc.setWhiningSpinnerInitialStrength(2.8F);
        assertEquals(2.8F, gc.getWhiningSpinnerInitialStrength(), 0.02);
        gc.setWhiningTwineCreationRate(2.9F);
        assertEquals(2.9F, gc.getWhiningTwineCreationRate(), 0.02);
    

        gc.setZombieInitialStrengthMin((short) 16);
        assertEquals(16, gc.getZombieInitialStrengthMin(), 0.02);
        gc.setZombieInitialStrengthMax((short) 17);
        assertEquals(17, gc.getZombieInitialStrengthMax(), 0.02);
        gc.setZombieInitialSpeedMax(3.1F);
        assertEquals(3.1F, gc.getZombieInitialSpeedMax(), 0.02);
        gc.setZombieInitialSpeedMin(3.2F);
        assertEquals(3.2F, gc.getZombieInitialSpeedMin(), 0.02);
        gc.setZombieSidewalkSpeedMultiplier(3.3F);
        assertEquals(3.3F, gc.getZombieSidewalkSpeedMultiplier(), 0.02);
        gc.setZombieCreationRate(3.4F);
        assertEquals(3.4F, gc.getZombieCreationRate(), 0.02);
        gc.setZombieCreationAcceleration(3.5F);
        assertEquals(3.5F, gc.getZombieCreationAcceleration(), 0.02);
        gc.setZombieEatingRate(3.6F);
        assertEquals(3.6F, gc.getZombieEatingRate(), 0.02);
        gc.setZombieStrengthIncreaseForEatingStudent(3.7F);
        assertEquals(3.7F, gc.getZombieStrengthIncreaseForEatingStudent(), 0.02);
        gc.setZombieStrengthIncreaseForExcuseGenerator(3.8F);
        assertEquals(3.8F, gc.getZombieStrengthIncreaseForExcuseGenerator(), 0.02);
        gc.setZombieStrengthIncreaseForWhiningSpinner(3.9F);
        assertEquals(3.9F, gc.getZombieStrengthIncreaseForWhiningSpinner(), 0.02);

        gc.setBombExcuseDamage((short) 18);
        assertEquals(18, gc.getBombExcuseDamage(), 0.02);
        gc.setBombTwinePerSquareOfDistance(4.0F);
        assertEquals(4.0F, gc.getBombTwinePerSquareOfDistance(), 0.02);
        gc.setBombDamageDiffusionFactor(4.1F);
        assertEquals(4.1F, gc.getBombDamageDiffusionFactor(), 0.02);

        gc.setTickLifetime((short) 19);
        assertEquals(19, gc.getTickLifetime(), 0.02);
        gc.setTicksToStrengthRatio(4.2F);
        assertEquals(4.2F, gc.getTicksToStrengthRatio(), 0.02);

    }

	@Test
	 public void GameConfiguration_CheckEncodeAndDecode() throws NotActiveException, Exception
    {
        GameConfiguration gc1 = new GameConfiguration();

        gc1.setPlayingFieldWidth((short) 50);
        gc1.setPlayingFieldHeight((short) 51);

        gc1.setBrilliantStudentRegistrationMin((short)10);
        gc1.setBrilliantStudentRegistrationMax((short)11);
        gc1.setExcuseGeneratorRegistrationMin((short)12);
        gc1.setExcuseGeneratorRegistrationMax((short)13);
        gc1.setWhiningSpinnerRegistrationMin((short) 14);
        gc1.setWhiningSpinnerRegistrationMax((short) 15);

        gc1.setBrilliantStudentInitialStrength(2.1F);
        gc1.setBrilliantStudentBaseSpeed(2.2F);
        gc1.setBrilliantStudentSidewalkSpeedMultiplier(2.3F);
        gc1.setBrilliantStudentDeathToZombieDelay(2.4F);

        gc1.setExcuseGeneratorInitialStrength(2.5F);
        

        gc1.setWhiningSpinnerInitialStrength(2.8F);
        gc1.setWhiningTwineCreationRate(2.9F);
    

        gc1.setZombieInitialStrengthMin((short)16);
        gc1.setZombieInitialStrengthMax((short)17);
        gc1.setZombieInitialSpeedMax(3.1F);
        gc1.setZombieInitialSpeedMin(3.2F);
        gc1.setZombieSidewalkSpeedMultiplier(3.3F);
        gc1.setZombieCreationRate(3.4F);
        gc1.setZombieCreationAcceleration(3.5F);
        gc1.setZombieEatingRate(3.6F);
        gc1.setZombieStrengthIncreaseForEatingStudent(3.7F);
        gc1.setZombieStrengthIncreaseForExcuseGenerator(3.8F);
        gc1.setZombieStrengthIncreaseForWhiningSpinner(3.9F);

        gc1.setBombExcuseDamage((short)18);
        gc1.setBombTwinePerSquareOfDistance(4.0F);
        gc1.setBombDamageDiffusionFactor(4.1F);

        gc1.setTickLifetime((short)19);
        gc1.setTicksToStrengthRatio(4.2F);

        ByteList bytes = new ByteList();
        gc1.Encode(bytes);
        
        GameConfiguration gc2 = GameConfiguration.Create(bytes);
        
        assertEquals(gc1.getPlayingFieldWidth(), gc2.getPlayingFieldWidth());
        assertEquals(gc1.getPlayingFieldHeight(), gc2.getPlayingFieldHeight());

        assertEquals(gc1.getBrilliantStudentRegistrationMin(), gc2.getBrilliantStudentRegistrationMin());
        assertEquals(gc1.getBrilliantStudentRegistrationMax(), gc2.getBrilliantStudentRegistrationMax());
        assertEquals(gc1.getExcuseGeneratorRegistrationMin(), gc2.getExcuseGeneratorRegistrationMin());
        assertEquals(gc1.getExcuseGeneratorRegistrationMax(), gc2.getExcuseGeneratorRegistrationMax());
        assertEquals(gc1.getWhiningSpinnerRegistrationMin(), gc2.getWhiningSpinnerRegistrationMin());
        assertEquals(gc1.getWhiningSpinnerRegistrationMax(), gc2.getWhiningSpinnerRegistrationMax());

        assertEquals(gc1.getBrilliantStudentInitialStrength(), gc2.getBrilliantStudentInitialStrength() ,0.02);
        assertEquals(gc1.getBrilliantStudentBaseSpeed(), gc2.getBrilliantStudentBaseSpeed(), 0.02);
        assertEquals(gc1.getBrilliantStudentSidewalkSpeedMultiplier(), gc2.getBrilliantStudentSidewalkSpeedMultiplier(),0.02);
        assertEquals(gc1.getBrilliantStudentDeathToZombieDelay(), gc2.getBrilliantStudentDeathToZombieDelay(),0.02);

        
        assertEquals(gc1.getExcuseGeneratorInitialStrength(), gc2.getExcuseGeneratorInitialStrength(),0.02);
       
      

        assertEquals(gc1.getWhiningSpinnerInitialStrength(), gc2.getWhiningSpinnerInitialStrength(),0.02);
      
       

        assertEquals(gc1.getZombieInitialStrengthMin(), gc2.getZombieInitialStrengthMin(),0.02);
        assertEquals(gc1.getZombieInitialStrengthMax(), gc2.getZombieInitialStrengthMax(),0.02);
        assertEquals(gc1.getZombieInitialSpeedMax(), gc2.getZombieInitialSpeedMax(),0.02);
        assertEquals(gc1.getZombieInitialSpeedMin(), gc2.getZombieInitialSpeedMin(),0.02);
        assertEquals(gc1.getZombieSidewalkSpeedMultiplier(), gc2.getZombieSidewalkSpeedMultiplier(),0.02);
        assertEquals(gc1.getZombieCreationRate(), gc2.getZombieCreationRate(),0.02);
        assertEquals(gc1.getZombieCreationAcceleration(), gc2.getZombieCreationAcceleration(),0.02);
        assertEquals(gc1.getZombieEatingRate(), gc2.getZombieEatingRate(),0.02);
        assertEquals(gc1.getZombieStrengthIncreaseForEatingStudent(), gc2.getZombieStrengthIncreaseForEatingStudent(),0.02);
        assertEquals(gc1.getZombieStrengthIncreaseForExcuseGenerator(), gc2.getZombieStrengthIncreaseForExcuseGenerator(),0.02);
        assertEquals(gc1.getZombieStrengthIncreaseForWhiningSpinner(), gc2.getZombieStrengthIncreaseForWhiningSpinner(),0.02);

        assertEquals(gc1.getBombExcuseDamage(), gc2.getBombExcuseDamage(),0.02);
        assertEquals(gc1.getBombTwinePerSquareOfDistance(), gc2.getBombTwinePerSquareOfDistance(),0.02);
        assertEquals(gc1.getBombDamageDiffusionFactor(), gc2.getBombDamageDiffusionFactor(),0.02);

        assertEquals(gc1.getTickLifetime(), gc2.getTickLifetime(),0.02);
        assertEquals(gc1.getTicksToStrengthRatio(), gc2.getTicksToStrengthRatio(),0.02);


        bytes.Clear();
        gc1.Encode(bytes);
        bytes.GetByte();            // Read one byte, which will throw the length off
        try
        {
        	gc2 = GameConfiguration.Create(bytes);
        	fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e) {}

        bytes.Clear();
        gc1.Encode(bytes);
        bytes.Add((byte)100);       // Add a byte
        bytes.GetByte();            // Read one byte, which will make the ID wrong
        
        try
        {
        	gc2 = GameConfiguration.Create(bytes);
        	fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e) {}
    }
}
