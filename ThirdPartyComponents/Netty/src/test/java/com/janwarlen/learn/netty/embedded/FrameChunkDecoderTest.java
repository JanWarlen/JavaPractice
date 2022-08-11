package com.janwarlen.learn.netty.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameChunkDecoderTest {

    @Test
    void decode() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        ByteBuf duplicate = buffer.duplicate();

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FrameChunkDecoder(3));
        assertTrue(embeddedChannel.writeInbound(duplicate.readBytes(2)));
        try {
            embeddedChannel.writeInbound(duplicate.readBytes(4));
            Assertions.fail();
        } catch (TooLongFrameException e) {
            System.out.println("-");
        }
        assertTrue(embeddedChannel.writeInbound(duplicate.readBytes(3)));
        assertTrue(embeddedChannel.finish());

        ByteBuf read = embeddedChannel.readInbound();
        assertEquals(buffer.readSlice(2), read);
        read.release();

        read = embeddedChannel.readInbound();
        assertEquals(buffer.skipBytes(4).readSlice(3), read);
        read.release();
        buffer.release();
    }
}