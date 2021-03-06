package skadistats.clarity.io.bitstream;

import com.google.protobuf.ByteString;
import com.google.protobuf.ZeroCopy;
import skadistats.clarity.ClarityException;
import skadistats.clarity.io.Util;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;

public abstract class UnsafeBitStreamBase extends BitStream {

    protected static final Unsafe unsafe;
    protected static final long base;

    static {
        Unsafe u = null;
        try {
            Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
            unsafeConstructor.setAccessible(true);
            u = unsafeConstructor.newInstance();
        } catch (Exception e) {
            Util.uncheckedThrow(e);
        }
        unsafe = u;
        base = unsafe.arrayBaseOffset(byte[].class);
    }

    protected final byte[] data;
    protected final long bound;

    public UnsafeBitStreamBase(ByteString input) {
        data = ZeroCopy.extract(input);
        pos = 0;
        len = data.length * 8;
        bound = ((data.length + 8) & 0xFFFFFFF8);
    }

    protected void checkAccessAbsolute(long offs, long n) {
        checkAccessRelative(offs - base, n);
    }

    protected void checkAccessRelative(long offs, long n) {
        if (offs < 0L) {
            accessFailed(offs);
        } else  if (offs + n > bound) {
            accessFailed(offs + n);
        }
    }

    private void accessFailed(long offs) {
        throw new ClarityException(
                "Invalid memory access: Tried to access array of length %d at offset %d", data.length, offs
        );
    }


}
