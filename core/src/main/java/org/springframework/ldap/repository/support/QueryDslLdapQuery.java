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

package org.springframework.ldap.repository.support;

import com.querydsl.core.DefaultQueryMetadata;
import com.querydsl.core.FilteredClause;
import com.querydsl.core.support.QueryMixin;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Spring LDAP specific {@link FilteredClause} implementation.
 *
 * @author Mattias Hellborg Arthursson
 * @author Eddu Melendez
 * @since 2.0
 */
public class QueryDslLdapQuery<K> implements FilteredClause<QueryDslLdapQuery<K>> {
    private final LdapOperations ldapOperations;
    private final Class<? extends K> clazz;
    private final String base;

    private QueryMixin<QueryDslLdapQuery<K>> queryMixin =
            new QueryMixin<QueryDslLdapQuery<K>>(this, new DefaultQueryMetadata().noValidate());

    private final LdapSerializer filterGenerator;

    @SuppressWarnings("unchecked")
    public QueryDslLdapQuery(LdapOperations ldapOperations, EntityPath<K> entityPath) {
        this(ldapOperations, (Class<K>) entityPath.getType());
    }

    public QueryDslLdapQuery(LdapOperations ldapOperations, Class<K> clazz) {
        this.ldapOperations = ldapOperations;
        this.clazz = clazz;
        this.filterGenerator = new LdapSerializer(ldapOperations.getObjectDirectoryMapper(), clazz);
        this.base = clazz.getAnnotation(Entry.class) != null ? clazz.getAnnotation(Entry.class).base() : null;
    }

    @Override
    public QueryDslLdapQuery<K> where(Predicate... o) {
        return queryMixin.where(o);
    }

    @SuppressWarnings("unchecked")
    public List<K> list() {
        return (List<K>) ldapOperations.find(buildQuery(), clazz);
    }

    public K uniqueResult() {
        return ldapOperations.findOne(buildQuery(), clazz);
    }

    LdapQuery buildQuery() {
	
	LdapQueryBuilder query = query();
	
	if (StringUtils.hasText(base)) {
	    query = query().base(base);
	}
	
        return query.filter(filterGenerator.handle(queryMixin.getMetadata().getWhere()));
    }

}
