/*
 * Copyright 2017 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.android.contentpal.predicates;

import android.support.annotation.NonNull;

import org.dmfs.android.contentpal.Predicate;


/**
 * A {@link Predicate} which evaluates to {@code true}, if and only if all of the given {@link Predicate}s evaluate to {@code true} as well. This corresponds to
 * the Boolean "and" operation.
 *
 * @author Marten Gajda
 */
public final class AllOf extends DelegatingPredicate
{
    public AllOf(@NonNull Predicate... predicates)
    {
        super(new BinaryPredicate("and", predicates));
    }


    public AllOf(@NonNull Iterable<Predicate> predicates)
    {
        super(new BinaryPredicate("and", predicates));
    }
}
