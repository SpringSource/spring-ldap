/*
 * Copyright 2005-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.ldap.pool.factory;

import org.junit.Test;
import org.springframework.ldap.pool.AbstractPoolTestCase;
import org.springframework.ldap.pool.MutableDelegatingLdapContext;

import javax.naming.directory.DirContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the MutablePoolingContextSource class.
 * 
 * @author Ulrik Sandberg
 */
public class MutablePoolingContextSourceTest extends AbstractPoolTestCase {

    @Test
	public void testGetReadOnlyLdapContext() throws Exception {

		when(contextSourceMock.getReadOnlyContext()).thenReturn(ldapContextMock);

		final MutablePoolingContextSource poolingContextSource = new MutablePoolingContextSource();
		poolingContextSource.setContextSource(contextSourceMock);

		// Get a context
		final DirContext result = poolingContextSource.getReadOnlyContext();

		assertThat(result.getClass()).isEqualTo(MutableDelegatingLdapContext.class);
	}
}
