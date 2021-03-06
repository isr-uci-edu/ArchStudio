package org.archstudio.bna.things.utility;

import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinateMapper;
import org.archstudio.bna.IThingPeer;
import org.archstudio.bna.keys.IThingKey;
import org.archstudio.bna.keys.ThingKey;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/*
 * DO NOT EDIT THIS FILE, it is automatically generated. ANY MODIFICATIONS WILL BE OVERWRITTEN. To modify, update the
 * thingdefinition extension at org.archstudio.bna/Package[name=org.archstudio.bna.things.utility]/Thing[name=Grid].
 */

@SuppressWarnings("all")
@NonNullByDefault
public abstract class GridThingBase extends org.archstudio.bna.things.AbstractThing
		implements org.archstudio.bna.IThing, org.archstudio.bna.facets.IHasMutableEdgeColor {

	public static final IThingKey<org.archstudio.bna.constants.GridDisplayType> GRID_DISPLAY_TYPE_KEY =
			ThingKey.create(com.google.common.collect.Lists.newArrayList("gridDisplayType", GridThingBase.class));

	public static final IThingKey<java.lang.Integer> GRID_SPACING_KEY =
			ThingKey.create(com.google.common.collect.Lists.newArrayList("gridSpacing", GridThingBase.class));

	public GridThingBase(@Nullable Object id) {
		super(id);
	}

	@Override
	public IThingPeer<? extends GridThing> createPeer(IBNAView view, ICoordinateMapper cm) {
		return new GridThingPeer<>((GridThing) this, view, cm);
	}

	@Override
	protected void initProperties() {
		initProperty(org.archstudio.bna.facets.IHasEdgeColor.EDGE_COLOR_KEY, new org.eclipse.swt.graphics.RGB(0, 0, 0));
		initProperty(org.archstudio.bna.things.utility.GridThing.GRID_DISPLAY_TYPE_KEY,
				org.archstudio.bna.constants.GridDisplayType.DOTTED_LINES);
		initProperty(org.archstudio.bna.things.utility.GridThing.GRID_SPACING_KEY, 24);
		super.initProperties();
	}

	@Override
	public @Nullable org.eclipse.swt.graphics.RGB getEdgeColor() {
		return get(org.archstudio.bna.facets.IHasEdgeColor.EDGE_COLOR_KEY);
	}

	@Override
	public void setEdgeColor(@Nullable org.eclipse.swt.graphics.RGB edgeColor) {
		set(org.archstudio.bna.facets.IHasEdgeColor.EDGE_COLOR_KEY, edgeColor);
	}

	public org.archstudio.bna.constants.GridDisplayType getGridDisplayType() {
		return get(org.archstudio.bna.things.utility.GridThing.GRID_DISPLAY_TYPE_KEY);
	}

	public void setGridDisplayType(org.archstudio.bna.constants.GridDisplayType gridDisplayType) {
		set(org.archstudio.bna.things.utility.GridThing.GRID_DISPLAY_TYPE_KEY, gridDisplayType);
	}

	public int getGridSpacing() {
		return get(org.archstudio.bna.things.utility.GridThing.GRID_SPACING_KEY);
	}

	public void setGridSpacing(int gridSpacing) {
		set(org.archstudio.bna.things.utility.GridThing.GRID_SPACING_KEY, gridSpacing);
	}
}