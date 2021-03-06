package skadistats.clarity.io.decoder;

import skadistats.clarity.io.bitstream.BitStream;

public class StringLenDecoder implements Decoder<String> {

    @Override
    public String decode(BitStream bs) {
        return bs.readString(bs.readUBitInt(9));
    }

}
