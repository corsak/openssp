package com.atg.openssp.common.buffer;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atg.openssp.common.configuration.Context;

/**
 * @author André Schmer
 */
public class SSPLatencyBuffer extends LongTypedBuffer implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(SSPLatencyBuffer.class);

	private static final SSPLatencyBuffer instance = new SSPLatencyBuffer();

	public static SSPLatencyBuffer getBuffer() {
		return instance;
	}

	private SSPLatencyBuffer() {
		super(100);
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			latch.await();
		} catch (final InterruptedException e) {
			log.error("{} latch interrupted", LocalDate.now(Context.zoneId));
			// clear state
			latch = null;
			// Moved into catch to make more clear interrupting throw after exception ...
			Thread.currentThread().interrupt();
		}
	}

}
