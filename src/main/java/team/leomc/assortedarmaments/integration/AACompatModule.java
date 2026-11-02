package team.leomc.assortedarmaments.integration;

import team.leomc.assortedarmaments.data.gen.tags.AAItemTagsProvider;

import java.util.List;

public interface AACompatModule {
	String id();

	boolean isLoaded();

	List<AAWeaponDefinition> weapons();

	default void addExtraTags(AAItemTagsProvider provider) {
	}
}
