package org.nanotek.bytebuddy.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.nanotek.ABase;
import org.nanotek.Base;

import net.bytebuddy.ByteBuddy;

public class TestBaseUUID {

	
	@Test
	@Order(1)
	void testABase() throws Exception{
		Optional<ABase> obase = Base.newInstance(ABase.class);
		obase.ifPresent(b -> {
			UUID id = b.withUUID();
			assertNotNull(id);
			System.err.println(id.toString());
			assertEquals(id.toString() , "7254bb4b-5a4a-3a8c-acbf-927f54c7ac6d");
		});
	}
	
	@Test
	@Order(2)
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
}
