/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.streaming.api.collector.ft;

import org.apache.flink.streaming.api.ft.layer.util.RecordId;
import org.apache.flink.util.Collector;
import org.apache.flink.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AckerCollector implements Collector<RecordId> {

	private static final Logger LOG = LoggerFactory.getLogger(AckerCollector.class);
	private boolean failFlag;

	public AckerCollector() {
		this.failFlag = false;
	}

	public void setFailFlag(boolean isFailed) {
		this.failFlag = isFailed;
	}

	@Override
	public void collect(RecordId xorMessage) {
		try {
			if (!failFlag) {
				emit(xorMessage);
			} else {
				failFlag = false;
				fail(xorMessage);
			}
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Emit to AckerTask failed due to: {}", StringUtils.stringifyException(e));
			}
		}
	}

	protected abstract void emit(RecordId recordId) throws Exception;

	protected abstract void fail(RecordId recordId) throws Exception;

	@Override
	public void close() {
	}

}
