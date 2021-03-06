package skadistats.clarity.io.decoder;

import skadistats.clarity.io.bitstream.BitStream;

public class FloatNoScaleDecoder implements Decoder<Float> {

    @Override
    public Float decode(BitStream bs) {
        return Float.intBitsToFloat(bs.readUBitInt(32));
    }

}
