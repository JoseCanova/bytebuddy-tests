package org.nanotek.bytebuddy.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.nanotek.ABase;
import org.nanotek.Base;
import org.nanotek.bytebuddy.GreetingInterceptor;
import org.springframework.boot.test.context.SpringBootTest;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;


@SpringBootTest
public class TestByteDuddy {

	
	@Test
	void testByteDuddyGreetingInterceptor() throws InstantiationException, IllegalAccessException {
		Class<? extends java.util.function.Function> dynamicType = new ByteBuddy()
				  .subclass(java.util.function.Function.class)
				  .method(ElementMatchers.named("apply"))
				  .intercept(MethodDelegation.to(new GreetingInterceptor()))
				  .make()
				  .load(getClass().getClassLoader())
				  .getLoaded();
		assertEquals((String) dynamicType.newInstance().apply("Byte Buddy"), "Hello from Byte Buddy");

	}
	
	@Test
	void testByteBuddyBase()  throws Exception {
		
		Class <? extends Base<?>> theBase = (Class<? extends Base<?>>) new ByteBuddy().subclass(Base.class)
				.make()
				.load(getClass().getClassLoader()).getLoaded();
		Optional<? extends Base<?>> theBase1 =  Base.newInstance(theBase);
		theBase1.ifPresent(b -> {
			UUID id = b.withUUID();
			assertNotNull(id);
			System.err.println(id.toString());
		});
	} 
	
	@Test
	void testABase() throws Exception{
		Optional<ABase> obase = Base.newInstance(ABase.class);
		obase.ifPresent(b -> {
			UUID id = b.withUUID();
			assertNotNull(id);
			System.err.println(id.toString());
		});
	}
	

}
