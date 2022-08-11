package com.janwarlen.learn.netty.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FixedLengthFrameDecoderTest {

    @Test
    void decode() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        ByteBuf duplicate = buffer.duplicate();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        // write
        Assertions.assertTrue(embeddedChannel.writeInbound(duplicate.retain()));
        // 标记为已完成
        Assertions.assertTrue(embeddedChannel.finish());
        // read
        ByteBuf byteBuf = embeddedChannel.readInbound();
        Assertions.assertEquals(buffer.readSlice(3), byteBuf);
        byteBuf.release();

        byteBuf = embeddedChannel.readInbound();
        Assertions.assertEquals(buffer.readSlice(3), byteBuf);
        byteBuf.release();

        byteBuf = embeddedChannel.readInbound();
        Assertions.assertEquals(buffer.readSlice(3), byteBuf);
        byteBuf.release();

        Assertions.assertNull(embeddedChannel.readInbound());
        buffer.release();
    }

    @Test
    void decode2() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        ByteBuf duplicate = buffer.duplicate();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        // 未达到length=3，返回的是false
        Assertions.assertFalse(embeddedChannel.writeInbound(duplicate.readBytes(2)));
        Assertions.assertTrue(embeddedChannel.writeInbound(duplicate.readBytes(7)));

        Assertions.assertTrue(embeddedChannel.finish());

        // read
        ByteBuf byteBuf = embeddedChannel.readInbound();
        Assertions.assertEquals(buffer.readSlice(3), byteBuf);
        byteBuf.release();

        byteBuf = embeddedChannel.readInbound();
        Assertions.assertEquals(buffer.readSlice(3), byteBuf);
        byteBuf.release();

        byteBuf = embeddedChannel.readInbound();
        Assertions.assertEquals(buffer.readSlice(3), byteBuf);
        byteBuf.release();

        Assertions.assertNull(embeddedChannel.readInbound());
        buffer.release();
    }
}