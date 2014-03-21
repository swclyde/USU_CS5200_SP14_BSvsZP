using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;

namespace CommonTester
{
    [TestClass]
    public class PlayingFieldLayoutTester
    {
        [TestMethod]
        public void PlayingFieldLayout_CheckConstructors()
        {
            PlayingFieldLayout pfl = new PlayingFieldLayout();
            Assert.AreEqual(0, pfl.Width);
            Assert.AreEqual(0, pfl.Height);
            Assert.IsNotNull(pfl.SidewalkSquares);

            pfl = new PlayingFieldLayout(20,25);
            Assert.AreEqual(20, pfl.Width);
            Assert.AreEqual(25, pfl.Height);
            Assert.IsNotNull(pfl.SidewalkSquares);            
        }

        [TestMethod]
        public void PlayingFieldLayout_CheckProperties()
        {
            PlayingFieldLayout pfl = new PlayingFieldLayout(20, 30);
            Assert.AreEqual(20, pfl.Width);
            Assert.AreEqual(30, pfl.Height);
            Assert.IsNotNull(pfl.SidewalkSquares);

            pfl.Width = 0;
            Assert.AreEqual(0, pfl.Width);
            pfl.Width = 35;
            Assert.AreEqual(35, pfl.Width);

            pfl.Height = 0;
            Assert.AreEqual(0, pfl.Height);
            pfl.Height = 45;
            Assert.AreEqual(45, pfl.Height);

            List<FieldLocation> flList = new List<FieldLocation> { new FieldLocation(1, 1), new FieldLocation(2, 1) };
            pfl.SidewalkSquares = flList;
            Assert.AreSame(flList, pfl.SidewalkSquares);
            pfl.SidewalkSquares = null;
            Assert.IsNull(pfl.SidewalkSquares);

        }

        [TestMethod]
        public void PlayingFieldLayout_CheckEncodeAndDecode()
        {
            PlayingFieldLayout pfl1 = new PlayingFieldLayout(20, 30);
            Assert.AreEqual(20, pfl1.Width);
            Assert.AreEqual(30, pfl1.Height);
            Assert.IsNotNull(pfl1.SidewalkSquares);

            List<FieldLocation> flList = new List<FieldLocation> { new FieldLocation(1, 1), new FieldLocation(2, 1) };
            pfl1.SidewalkSquares = flList;
            Assert.AreSame(flList, pfl1.SidewalkSquares);

            ByteList bytes = new ByteList();
            pfl1.Encode(bytes);
            PlayingFieldLayout pfl2 = PlayingFieldLayout.Create(bytes);
            Assert.AreEqual(pfl1.Width, pfl2.Width);
            Assert.AreEqual(pfl1.Height, pfl2.Height);
            Assert.IsNotNull(pfl2.SidewalkSquares);
            Assert.AreEqual(pfl1.SidewalkSquares.Count, pfl1.SidewalkSquares.Count);

            bytes.Clear();
            pfl1.Encode(bytes);
            bytes.GetByte();            // Read one byte, which will throw the length off
            try
            {
                pfl2 = PlayingFieldLayout.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

            bytes.Clear();
            pfl1.Encode(bytes);
            bytes.Add((byte)100);       // Add a byte
            bytes.GetByte();            // Read one byte, which will make the ID wrong
            try
            {
                pfl2 = PlayingFieldLayout.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

        }
    }
}
