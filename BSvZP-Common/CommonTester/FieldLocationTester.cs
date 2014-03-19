using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;

namespace CommonTester
{
    [TestClass]
    public class FieldLocationTester
    {
        [TestMethod]
        public void FieldLocation_CheckContructors()
        {
            FieldLocation loc = new FieldLocation();
            Assert.AreEqual(0, loc.X);
            Assert.AreEqual(0, loc.Y);
            Assert.AreEqual(false, loc.Immutable);

            loc = new FieldLocation(10, 20);
            Assert.AreEqual(10, loc.X);
            Assert.AreEqual(20, loc.Y);
            Assert.AreEqual(false, loc.Immutable);

            loc = new FieldLocation(-20, -30, true);
            Assert.AreEqual(-20, loc.X);
            Assert.AreEqual(-30, loc.Y);
            Assert.AreEqual(true, loc.Immutable);

            loc = new FieldLocation(false);
            Assert.AreEqual(0, loc.X);
            Assert.AreEqual(0, loc.Y);
            Assert.AreEqual(false, loc.Immutable);

            loc = new FieldLocation(true);
            Assert.AreEqual(0, loc.X);
            Assert.AreEqual(0, loc.Y);
            Assert.AreEqual(true, loc.Immutable);

            loc = new FieldLocation { X = 10, Y = 30 };
            Assert.AreEqual(10, loc.X);
            Assert.AreEqual(30, loc.Y);
            Assert.AreEqual(false, loc.Immutable);

            loc = new ImmutableFieldLocation();
            Assert.AreEqual(0, loc.X);
            Assert.AreEqual(0, loc.Y);
            Assert.AreEqual(true, loc.Immutable);

            loc = new ImmutableFieldLocation { X = 0, Y = 0 };
            Assert.AreEqual(0, loc.X);
            Assert.AreEqual(0, loc.Y);
            Assert.AreEqual(true, loc.Immutable);

            loc = new ImmutableFieldLocation { X = 100, Y = 200 };
            Assert.AreEqual(100, loc.X);
            Assert.AreEqual(200, loc.Y);
            Assert.AreEqual(true, loc.Immutable);

            loc = new ImmutableFieldLocation { X = -200, Y = -300 };
            Assert.AreEqual(-200, loc.X);
            Assert.AreEqual(-300, loc.Y);
            Assert.AreEqual(true, loc.Immutable);
        }

        [TestMethod]
        public void FieldLocation_CheckProperties()
        {
            FieldLocation loc = new FieldLocation();
            Assert.AreEqual(0, loc.X);
            Assert.AreEqual(0, loc.Y);
            Assert.AreEqual(false, loc.Immutable);

            loc.X = 100;
            Assert.AreEqual(100, loc.X);
            loc.X = -200;
            Assert.AreEqual(-200, loc.X);
            loc.X = 0;
            Assert.AreEqual(0, loc.X);


            loc.Y = 100;
            Assert.AreEqual(100, loc.Y);
            loc.Y = -200;
            Assert.AreEqual(-200, loc.Y);
            loc.Y = 0;
            Assert.AreEqual(0, loc.Y);

            loc = new ImmutableFieldLocation { X = 10, Y = 20 };
            Assert.AreEqual(10, loc.X);
            Assert.AreEqual(20, loc.Y);
            Assert.AreEqual(true, loc.Immutable);

            loc.X = 100;
            Assert.AreEqual(10, loc.X);
            loc.X = -200;
            Assert.AreEqual(10, loc.X);
            loc.X = 0;
            Assert.AreEqual(10, loc.X);

            loc.Y = 100;
            Assert.AreEqual(20, loc.Y);
            loc.Y = -200;
            Assert.AreEqual(20, loc.Y);
            loc.Y = 0;
            Assert.AreEqual(20, loc.Y);
        }

        [TestMethod]
        public void FieldLocation_CheckEncodeDecode()
        {
            ByteList bytes = new ByteList();

            FieldLocation loc1 = new FieldLocation { X = 100, Y = 200 };
            loc1.Encode(bytes);
            Assert.AreEqual(9, bytes.Length);
            Assert.AreEqual(3, bytes[0]);
            Assert.AreEqual(238, bytes[1]);
            Assert.AreEqual(0, bytes[2]);
            Assert.AreEqual(5, bytes[3]);
            Assert.AreEqual(0, bytes[4]);
            Assert.AreEqual(100, bytes[5]);
            Assert.AreEqual(0, bytes[6]);
            Assert.AreEqual(200, bytes[7]);
            Assert.AreEqual(0, bytes[8]);

            FieldLocation loc2 = FieldLocation.Create(bytes);
            Assert.AreEqual(loc1.X, loc2.X);
            Assert.AreEqual(loc1.Y, loc2.Y);
            Assert.AreEqual(false, loc2.Immutable);
        }

    }
}
