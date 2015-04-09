package com.raghunadimpalli.cc.core.components;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;
import com.raghunadimpalli.common.core.abstracts.ComponentActionBuilder;
import com.raghunadimpalli.common.core.abstracts.ComponentBuilderResolver;
import com.raghunadimpalli.common.core.abstracts.ComponentDataBuilder;
import com.raghunadimpalli.common.core.abstracts.ComponentParams;

public class DefaultComponentBuilderResolver implements
		ComponentBuilderResolver, BeanFactoryAware {
	
	private BeanFactory beanFactory;
	
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	@Override
	public ComponentDataBuilder resolveDataBuilder(String componentId,ComponentParams params) throws ApplicationException{
		ComponentDataBuilder result = null;
		if(beanFactory.containsBean(componentId)){
			result = (ComponentDataBuilder) beanFactory.getBean(componentId,ComponentDataBuilder.class);
		}
		return result;
	}
	
	@Override
	public ComponentActionBuilder resolveActionBuilder(String componentId,ComponentParams params) throws ApplicationException{
		ComponentActionBuilder result = null;
		if(beanFactory.containsBean(componentId)){
			result = (ComponentActionBuilder) beanFactory.getBean(componentId,ComponentActionBuilder.class);
		}
		return result;
	}

}
