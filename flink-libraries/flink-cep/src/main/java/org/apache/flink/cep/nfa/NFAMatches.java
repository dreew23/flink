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
package org.apache.flink.cep.nfa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * Wrapper for results of {@link NFA#process(Object, long)}.
 * @param <T> type of processed events
 */
public class NFAMatches<T> {

	private Collection<Map<String, T>> matches = new ArrayList<>();
	private Collection<Map<String, T>> discardedMatches = new HashSet<>();
	private Collection<Tuple2<Map<String, T>, Long>> timeoutedMatches = new ArrayList<>();

	void addMatch(final Collection<Map<String, T>> match) {
		matches.addAll(match);
	}

	void addDiscardedMatch(final Collection<Map<String, T>> discardedMatch) {
		discardedMatches.addAll(discardedMatch);
	}

	void addTimeoutedMatch(final Collection<Map<String, T>> timeoutedMatch, final long timestamp) {
		for (Map<String, T> timeoutPattern : timeoutedMatch) {
			timeoutedMatches.add(Tuple2.of(timeoutPattern, timestamp));
		}
	}

	/**
	 * @return Found positive matches
	 */
	public Collection<Map<String, T>> getMatches() {
		return matches;
	}

	/**
	 * @return Found matches that was ended with a stop state
	 */
	public Collection<Map<String, T>> getDiscardedMatches() {
		return discardedMatches;
	}

	/**
	 * @return Matches that were timeouted before completing
	 */
	public Collection<Tuple2<Map<String, T>, Long>> getTimeoutedMatches() {
		return timeoutedMatches;
	}
}
