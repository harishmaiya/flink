/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.io.network.multicast;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * The MulticastMessage is the predefined object to be used for multicasting.
 * It can contain an original message, when f0.lenth==0.
 * But it can represent more original messages blocked into one {@link MulticastMessage}.
 */

public class MulticastMessage extends Tuple2<long[], Double> {

	public MulticastMessage() {
		super();
	}

	public MulticastMessage(long[] targetIds, Double value) {
		super(targetIds, value);
	}

	public static KeySelector<MulticastMessage, Long> getKeySelector() {
		return new KeySelector<MulticastMessage, Long>() {

			@Override
			public Long getKey(MulticastMessage value) throws Exception {
//				System.out.println("KeySelector print: "+value);
				return value.f0[0];
			}
		};
	}
}