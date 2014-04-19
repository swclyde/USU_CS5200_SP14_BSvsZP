package Common;

import java.io.NotActiveException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.ArrayUtils;
import org.omg.CORBA.portable.ApplicationException;

public class ByteList {

    public static final boolean DEBUG = false; //flag for printing stuff to screen

    private static final Logger log = Logger.getLogger(ByteList.class.getName());
    private static final int SECTION_SIZE = 1024;

    private ArrayList<byte[]> _sections = new ArrayList<>();
    private short _addCurrentSection = (short) 0;
    private short _addCurrentOffset = (short) 0;
    private short _readCurrentPosition = (short) 0;
    private Stack<Short> _readLimitStack = new Stack<>();

    private int RemainingToRead;
    private short CurrentWritePosition;

    public ArrayList<byte[]> get_sections() {
        return _sections;
    }

    public ByteList() {
        _sections.add(new byte[SECTION_SIZE]);

    }

    public ByteList(Object... items) throws NotActiveException, UnknownHostException, Exception {
        _sections.add(new byte[SECTION_SIZE]);
        AddObjects(items);
    }

    public void WriteInt16To(int writePosition, short value) throws UnknownHostException {
        if (writePosition >= 0 && writePosition < (getLength() - 2)) {
            int sectionIdx = writePosition / SECTION_SIZE;
            int sectionOffset = writePosition - sectionIdx * SECTION_SIZE;

            byte[] bytes = BitConverter.getBytes(value);
            System.arraycopy(bytes, 0, // Source
                    _sections.get(sectionIdx), sectionOffset, // Destination
                    bytes.length);                    	      // Length Short.SIZE
        }
    }

    public void CopyFromBytes(byte[] bytes) {
        Clear();
        Add(bytes);
    }

    public void AddObjects(Object... items) throws NotActiveException, UnknownHostException, Exception {
        for (Object item : items) {
            AddObject(item);
        }
    }

    public void AddObject(Object item) throws Exception {
        if (item != null) {
            if (item instanceof ByteList) {
                Add((ByteList) item);
            } else if (item instanceof Boolean) {
                Add((boolean) item);
            } else if (item instanceof Byte) {
                Add((byte) item);
            } else if (item instanceof Character) {
                Add((char) item);
            } else if (item instanceof Short) {
                Add((short) item);
            } else if (item instanceof Integer) {
                Add((Integer) item);
            } else if (item instanceof Long) {
                Add((long) item);
            } else if (item instanceof Double) {
                Add((double) item);
            } else if (item instanceof Float) {
                Add((float) item);
            } else if (item instanceof String) {
                Add((String) item);
            } else if (item instanceof byte[]) {
                Add((byte[]) item);
            } else if (item instanceof DistributableObject) {
                Add((DistributableObject) item);
            } else {
                throw new NotActiveException();
            }
        } else {
            Add((DistributableObject) item);
        }
    }

    public void Add(ByteList value) {
        if (value != null) {
            for (int i = 0; i <= value.get_addCurrentSection(); i++) {
                if (i < value.get_addCurrentSection()) {
                    Add(value._sections.get(i), 0, SECTION_SIZE);
                } else {
                    Add(value._sections.get(i), 0, value.get_addCurrentOffset());
                }
            }
        }
    }

    public void Add(byte value) {
        Add(new byte[]{value});
    }

    public void Add(Boolean value) {
        if (value) {
            Add(new byte[]{1});
        } else {
            Add(new byte[]{0});
        }
    }

    public void Add(char value) {
        Add(BitConverter.getBytes(value));
    }

    public void Add(short value) {
        Add(BitConverter.getBytes(value));
    }

    public void Add(int value) {
        Add(BitConverter.getBytes(value));
    }

    public void Add(long value) {
        Add(BitConverter.getBytes(value));
    }

    public void Add(double value) {
        byte[] bytes = BitConverter.getBytes(value);
        if (BitConverter.IsLittleEndian) {
            ArrayUtils.reverse(bytes);
        }
        Add(bytes);
    }

    public void Add(float value) {
        byte[] bytes = BitConverter.getBytes(value);
        if (BitConverter.IsLittleEndian) {
            ArrayUtils.reverse(bytes);
        }
        Add(bytes);
    }

    public void Add(String value) throws UnsupportedEncodingException, ApplicationException {
        if ((value != null) && (value.length() != 0)) {
            byte[] bytes = value.getBytes(Charset.forName("UTF-16"));
            Add((short) (bytes.length - 2));
            byte[] tmp = new byte[bytes.length - 2];

            for (int i = 0; i < bytes.length - 2; i += 2) {
                tmp[i] = bytes[i + 3];
                tmp[i + 1] = bytes[i + 2];
            }
            Add(tmp);
        } else {
            Add((short) 0);
        }
    }

    public void Add(byte[] value) {
        if (value != null) {
            Add(value, 0, value.length);
        }
    }

    public void Add(byte[] value, int offset, int length) {
        if (value != null) {
            int additionalBytesNeeded = get_addCurrentOffset() + getLength() - SECTION_SIZE;
            Grow(additionalBytesNeeded);

            int cnt = 0;
            while (cnt < length) {
                short blockSize = (short) Math.min(SECTION_SIZE - get_addCurrentOffset(), length - cnt);
                System.arraycopy(value, offset + cnt, _sections.get(get_addCurrentSection()), get_addCurrentOffset(), blockSize);

                cnt += blockSize;
                short temp1 = (short) (get_addCurrentOffset() + blockSize);
                set_addCurrentOffset(temp1);
                if (this.get_addCurrentOffset() == SECTION_SIZE) {
                    set_addCurrentOffset((short) 0);
                    short temp = (short) (get_addCurrentSection() + (short) 1);
                    set_addCurrentSection(temp);
                }
            }
        }
    }

    public void Add(DistributableObject obj) throws UnknownHostException, Exception {
        if (obj == null) {
            Add(false);
        } else {
            Add(true);
            this.update();
            obj.Encode(this);
        }
    }

    public void ResetRead() {
        set_readCurrentPosition((short) 0);
    }

    public ByteList GetByteList(int length) throws ApplicationException {
        ByteList result = new ByteList();
        result.CopyFromBytes(GetBytes(length));
        return result;
    }

    public byte GetByte() throws ApplicationException {
        return GetBytes(1)[0];
    }

    public Boolean GetBool() throws ApplicationException {
        return (GetBytes(1)[0] == 0) ? false : true;
    }

    public char GetChar() throws Exception {
        return BitConverter.toChar(GetBytes(2), 0);
    }

    public short GetInt16() throws Exception {
        return BitConverter.toInt16(GetBytes(2), 0);
    }

    public short PeekInt16() throws Exception {
        short result = BitConverter.toInt16(GetBytes(2), 0);
        short temp = (short) (this.get_readCurrentPosition() - 2);
        this.set_readCurrentPosition(temp);   // Move the current read position back two bytes
        return result;
    }

    public int GetInt32() throws Exception {
        return BitConverter.toInt32(GetBytes(4), 0);
    }

    public long GetInt64() throws Exception {
        return BitConverter.toInt64(GetBytes(8), 0);
    }

    public double GetDouble() throws Exception {
        byte[] bytes = GetBytes(8);
        if (BitConverter.IsLittleEndian) {
            ArrayUtils.reverse(bytes);
        }
        return BitConverter.toDouble(bytes, 0);
    }

    public float GetFloat() throws Exception {
        byte[] bytes = GetBytes(4);
        if (BitConverter.IsLittleEndian) {
            ArrayUtils.reverse(bytes);
        }
        return BitConverter.toSingle(bytes, 0);
    }

    public String GetString() throws Exception {
        String result = "";
        short length = GetInt16();

        if (length > 0) {
            byte[] tmp = GetBytes(length);
            byte[] bytes = new byte[tmp.length + 2];
            bytes[0] = -2;
            bytes[1] = -1;
            for (int i = 0; i < tmp.length; i += 2) {
                bytes[i + 3] = tmp[i];
                bytes[i + 2] = tmp[i + 1];
            }

            result = new String(bytes, "UTF-16");
        }

        if (DEBUG) {
            System.out.println("result from ByteList.GetString() is: " + result);
        }
        return result;
    }

    public int getRemainingToRead() {
        int tmpMax = (this._readLimitStack.size() == 0) ? this.getLength() : this._readLimitStack.peek();
        RemainingToRead = (this.get_readCurrentPosition() >= tmpMax) ? 0 : tmpMax - (this.get_readCurrentPosition());
        if (DEBUG) {
            System.out.println("ByteList.RemainingToRead:" + RemainingToRead);
        }
        return RemainingToRead;
    }

    public byte[] GetBytes(int length) throws ApplicationException {
        if ((this._readLimitStack.size() > 0) && ((this.get_readCurrentPosition() + length) > this._readLimitStack.peek())) {
            throw new ApplicationException("Attempt to read beyond read limit", null);
        }

        byte[] result = new byte[length];
        int bytesRead = 0;

        while (bytesRead < length) {
            int sectionIndex = this.get_readCurrentPosition() / SECTION_SIZE;
            int sectionOffset = this.get_readCurrentPosition() - sectionIndex * SECTION_SIZE;

            int cnt = Math.min(SECTION_SIZE - sectionOffset, length - bytesRead);
            System.arraycopy(_sections.get(sectionIndex), sectionOffset, result, bytesRead, cnt);

            sectionOffset = 0;
            short temp = (short) (this.get_readCurrentPosition() + (short) cnt);
            this.set_readCurrentPosition(temp);
            bytesRead += cnt;
        }

        return result;
    }

    public DistributableObject GetDistributableObject() throws Exception {
        DistributableObject obj = null;
        boolean isPresent = GetBool();

        if (isPresent) {
            obj = DistributableObject.Create(this);
        }

        return obj;
    }

    public byte[] ToBytes() {
        int bytesRead = 0;
        byte[] bytes = new byte[getLength()];

        for (int i = 0; i <= get_addCurrentSection(); i++) {
            int sectionBytes = SECTION_SIZE;
            if (i == get_addCurrentSection()) {
                sectionBytes = get_addCurrentOffset();
            }
            System.arraycopy(_sections.get(i), 0, bytes, bytesRead, sectionBytes);
            bytesRead += sectionBytes;
        }

        return bytes;
    }

    public void SetNewReadLimit(short length) {
        _readLimitStack.push((short) (this.get_readCurrentPosition() + length));
    }

    public void RestorePreviosReadLimit() {
        if (_readLimitStack.size() > 0) {
            _readLimitStack.pop();
        }
    }

    public void ClearMaxReadPosition() {
        _readLimitStack.clear();
    }

    public void Clear() {
        _sections.clear();
        _readLimitStack.clear();
        set_addCurrentSection((short) 0);
        set_addCurrentOffset((short) 0);
        set_readCurrentPosition((short) 0);
    }

    public byte getByteValue(int index) throws Exception {
        if ((index < 0) || (index >= getLength())) {
            throw new IndexOutOfBoundsException();
        }
        int[] holder = {index};
        holder[0] = index;
        byte[] section = GetSection(holder);
        return section[index];
    }

    public void setByteValue(byte value, int index[]) throws ApplicationException {

        if (index[0] < 0) {
            throw new IndexOutOfBoundsException();
        }
        int[] holder = {index[0]};
        Grow(index[0]);
        byte[] section = GetSection(holder);
        section[index[0]] = value;
    }

    public int getLength() {
        return this.get_addCurrentSection() * SECTION_SIZE + this.get_addCurrentOffset();
    }

    public Boolean IsMore() {
        int tmpMax = (_readLimitStack.size() == 0) ? this.getLength() : _readLimitStack.peek();
        return (this.get_readCurrentPosition() >= tmpMax) ? false : true;
    }

    public void Log() {
        log.setLevel(Level.INFO);
        for (int i = 0; i < this.getLength(); i++) {
            try {
                log.info(Integer.toString(i) + "\t= " + this.getByteValue(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Private Methods
    private void Grow(int additionBytesNeeded) {
        int sectionIndex = (additionBytesNeeded / SECTION_SIZE) + 1;
        for (int i = _sections.size() - 1; i < sectionIndex; i++) {
            _sections.add(new byte[SECTION_SIZE]);
        }
    }

    private byte[] GetSection(int[] container) throws ApplicationException {
        int sectionIndex = container[0] / SECTION_SIZE;

        if (sectionIndex >= _sections.size()) {
            throw new ApplicationException(null, null);
        }

        container[0] -= sectionIndex * SECTION_SIZE;
        return _sections.get(sectionIndex);
    }

    static public String GetString(byte[] bytes, int[] container, Boolean isNullTerminated) throws Exception {
        short length = BitConverter.toInt16(bytes, container[0]);
        container[0] += 2;
        String result = new String(bytes, container[0], length, "UTF-16");
        container[0] += length;
        if (isNullTerminated) {
            container[0]++;
        }
        return result;

    }

    public short getCurrentWritePosition() {
        CurrentWritePosition = (short) (get_addCurrentSection() * SECTION_SIZE + get_addCurrentOffset());
        if (DEBUG) {
            System.out.println("ByteList.CurrentWritePosition: " + CurrentWritePosition);
        }
        return CurrentWritePosition;
    }

    public short get_readCurrentPosition() {
        return _readCurrentPosition;
    }

    public void set_readCurrentPosition(short _readCurrentPosition) {
        this._readCurrentPosition = _readCurrentPosition;
    }

    public short get_addCurrentSection() {
        return _addCurrentSection;
    }

    public void set_addCurrentSection(short _addCurrentSection) {
        this._addCurrentSection = _addCurrentSection;
    }

    public short get_addCurrentOffset() {
        return _addCurrentOffset;
    }

    public void set_addCurrentOffset(short _addCurrentOffset) {
        this._addCurrentOffset = _addCurrentOffset;
    }
    
    
    public void update() {
        if (DEBUG) {

            System.out.println("=============================");
            System.out.println("ByteList.update() Method>>>");
            System.out.println("_addCurrentSection() = " + this.get_addCurrentSection());
            System.out.println("CurrentWritePosition() = " + this.getCurrentWritePosition());
            System.out.println("Length() = " + this.getLength());
            System.out.println("RemainingToRead() = " + this.getRemainingToRead());
            System.out.println("_readCurrentPosition = " + this.get_readCurrentPosition());
            System.out.println("=============================");
        }
    }

}
