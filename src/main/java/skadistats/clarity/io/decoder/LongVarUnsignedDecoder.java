package skadistats.clarity.io.decoder;

import skadistats.clarity.io.bitstream.BitStream;

public class LongVarUnsignedDecoder implements Decoder<Long> {

    @Override
    public Long decode(BitStream bs) {
        return bs.readVarULong();
    }

}
