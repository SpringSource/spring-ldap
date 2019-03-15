/*
 * Copyright 2005-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ldap.itest.repositories;

import org.springframework.ldap.itest.odm.Person;
import org.springframework.ldap.repository.Query;
import org.springframework.ldap.repository.LdapRepository;

/**
 * @author Mattias Hellborg Arthursson
 */
public interface PersonRepository extends LdapRepository<Person> {
    @Query("(sn={0})")
    Iterable<Person> findByLastName(String lastName);

    @Query("(uid={0})")
    Person findByUid(String uid);

    Person findByTelephoneNumber(String phoneNumber);
}
