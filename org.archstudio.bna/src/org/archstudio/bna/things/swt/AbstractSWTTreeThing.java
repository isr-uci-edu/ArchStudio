package org.archstudio.bna.things.swt;

import org.archstudio.bna.facets.IHasMutableValue;

public abstract class AbstractSWTTreeThing<V> extends AbstractSWTThing implements IHasMutableValue<V> {

	public AbstractSWTTreeThing(Object id) {
		super(id);
	}

	public void setValue(V value) {
		set(VALUE_KEY, value);
	}

	@SuppressWarnings("unchecked")
	public V getValue() {
		return (V) get(VALUE_KEY);
	}
}
