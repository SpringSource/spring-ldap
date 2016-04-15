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
package org.springframework.ldap.core;

import org.junit.Before;
import org.junit.Test;

import javax.naming.Binding;
import javax.naming.NamingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContextMapperCallbackHandlerTest {

    private ContextMapper mapperMock;

    private ContextMapperCallbackHandler tested;

    @Before
    public void setUp() throws Exception {
        mapperMock = mock(ContextMapper.class);
        tested = new ContextMapperCallbackHandler(mapperMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmptyArgument() {
        new ContextMapperCallbackHandler(null);
    }

    @Test
    public void testGetObjectFromNameClassPair() throws NamingException {
        Object expectedObject = "object";
        Object expectedResult = "result";
        Binding expectedBinding = new Binding("some name", expectedObject);

        when(mapperMock.mapFromContext(expectedObject)).thenReturn(expectedResult);
        Object actualResult = tested
                .getObjectFromNameClassPair(expectedBinding);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test(expected = ObjectRetrievalException.class)
    public void testGetObjectFromNameClassPairObjectRetrievalException() throws NamingException {
        Binding expectedBinding = new Binding("some name", null);
        tested.getObjectFromNameClassPair(expectedBinding);
    }
}
