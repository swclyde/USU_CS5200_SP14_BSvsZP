using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;

namespace CommonTester
{
    [TestClass]
    public class ByteListTester
    {
        [TestMethod]
        public void ByteList_TestConstuctors()
        {
            // Check out the default constructor
            ByteList myBytes = new ByteList();
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(0, myBytes.Length);

            // Check out the general constructor that take any number of objects
            // Case 1: A single boolean object
            myBytes = new ByteList(true);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(1, myBytes.Length);
            Assert.AreEqual(1, myBytes[0]);

            // Case 2: 3 different objects
            myBytes = new ByteList(true, 123, "Hello");
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(1 + 4 + (2 + 2*5), myBytes.Length);

            // Case 3: 3 strings of lengths 5, 5, and 52
            myBytes = new ByteList("Hello", "There", "You amazing software developer and brilliant student");
            Assert.IsNotNull(myBytes);
            Assert.AreEqual((2 + 2*5) + (2 + 2*5) + (2 + 2*52), myBytes.Length);

            // Case 4: with a bunch of other parameters types
            ByteList moreBytes = new ByteList(  myBytes,
                                                (Int16)10,
                                                (Int64)20,
                                                (Single)30.0,
                                                (Double)40.0, 
                                                new byte[] { 1, 2, 3 });
            Assert.IsNotNull(moreBytes);
            Assert.AreEqual(myBytes.Length + 2 + 8 + 4 + 8 + 3, moreBytes.Length);

        }

        [TestMethod]
        public void ByteList_WriteAndAddMethods()
        {
            ByteList myBytes = new ByteList();

            // Case: Write out a boolean of True
            myBytes.Clear();
            myBytes.Add(true);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(1, myBytes.Length);
            Assert.AreEqual(1, myBytes[0]);

            // Case: Write out a boolean of False
            myBytes.Clear();
            myBytes.Add(false);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(1, myBytes.Length);
            Assert.AreEqual(0, myBytes[0]);

            // Case: Write out a Byte
            myBytes.Clear();
            myBytes.Add((byte)4);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(1, myBytes.Length);
            Assert.AreEqual((byte) 4, myBytes[0]);

            // Case: Write out a Char
            myBytes.Clear();
            myBytes.Add('A');
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(2, myBytes.Length);
            Assert.AreEqual(65, myBytes[0]);
            Assert.AreEqual(0, myBytes[1]);

            // Case: Write out a Int16
            myBytes.Clear();
            myBytes.Add((Int16) 7);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(2, myBytes.Length);
            Assert.AreEqual(0, myBytes[0]);
            Assert.AreEqual(7, myBytes[1]);

            // Case: Write out a Int16
            myBytes.Clear();
            myBytes.Add(Int16.MaxValue);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(2, myBytes.Length);
            Assert.AreEqual(127, myBytes[0]);
            Assert.AreEqual(255, myBytes[1]);

            // Case: Write out a Int32
            myBytes.Clear();
            myBytes.Add((Int32) 7);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(4, myBytes.Length);
            for (int i = 0; i < 3; i++) Assert.AreEqual(0, myBytes[i]);
            Assert.AreEqual(7, myBytes[3]);

            // Case: Write out a Int32
            myBytes.Clear();
            myBytes.Add(Int32.MaxValue);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(4, myBytes.Length);
            Assert.AreEqual(127, myBytes[0]);
            for (int i = 1; i < 4; i++) Assert.AreEqual(255, myBytes[i]);

            // Case: Write out a Int64
            myBytes.Clear();
            myBytes.Add((Int64) 7);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(8, myBytes.Length);
            for (int i=0; i<7; i++) Assert.AreEqual(0, myBytes[i]);
            Assert.AreEqual(7, myBytes[7]);

            // Case 7: Write out a Int64
            myBytes.Clear();
            myBytes.Add(Int64.MaxValue);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(8, myBytes.Length);
            Assert.AreEqual(127, myBytes[0]);
            for (int i = 1; i < 8; i++) Assert.AreEqual(255, myBytes[i]);

            // Case: Write out a Single Precision Real
            myBytes.Clear();
            myBytes.Add((float) 7.7 );
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(4, myBytes.Length);

            // Case: Write out a Double Precision Real
            myBytes.Clear();
            myBytes.Add((float)7.7);
            Assert.IsNotNull(myBytes);
            Assert.AreEqual(4, myBytes.Length);

            // Case: Write out a Byte Array
            myBytes.Clear();
            myBytes.Add(new byte[] { 1, 2, 3, 4, 5, 6 });
            Assert.AreEqual(6, myBytes.Length);
            for (int i = 0; i < 6; i++) Assert.AreEqual(i+1, myBytes[i]);

            // Case: Write out a string
            myBytes.Clear();
            myBytes.Add((string) null);
            Assert.AreEqual(2, myBytes.Length);
            Assert.AreEqual(0, myBytes[0]);
            Assert.AreEqual(0, myBytes[1]);

            // Case: Write out a string
            myBytes.Clear();
            myBytes.Add(string.Empty);
            Assert.AreEqual(2, myBytes.Length);
            Assert.AreEqual(0, myBytes[0]);
            Assert.AreEqual(0, myBytes[1]);

            // Case 11: Write out a string
            myBytes = new ByteList("abc");
            Assert.AreEqual(2 + 2*3, myBytes.Length);
            Assert.AreEqual(0, myBytes[0]);
            Assert.AreEqual(2*3, myBytes[1]);

            // Note AddObjects and AddObject methods were tested with constructors
        }

        [TestMethod]
        public void ByteList_GetMethods()
        {
            // TODO: Implement
        }

        [TestMethod]
        public void ByteList_OtherPublicMethods()
        {
            // TODO: Implement
        }

        [TestMethod]
        [ExpectedException(typeof(IndexOutOfRangeException))]
        public void ByteList_IndexOperator()
        {
            ByteList myBytes = new ByteList("abc");
            byte x = myBytes[-1];
        }


    }
}
