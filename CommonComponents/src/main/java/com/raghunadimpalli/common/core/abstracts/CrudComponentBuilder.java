package com.raghunadimpalli.common.core.abstracts;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public interface CrudComponentBuilder<E> extends ComponentDataBuilder {
		ComponentResponse<E> create(Object data,ComponentParams param) throws ApplicationException;
		ComponentResponse<E> update(Object data,ComponentParams param) throws ApplicationException;
		ComponentResponse<E> delete(Object data,ComponentParams param) throws ApplicationException;
}
