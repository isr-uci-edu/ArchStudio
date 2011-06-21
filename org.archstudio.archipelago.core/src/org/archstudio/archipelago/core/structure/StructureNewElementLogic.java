package org.archstudio.archipelago.core.structure;

import org.archstudio.archipelago.core.ArchipelagoUtils;
import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.IThing;
import org.archstudio.bna.logics.AbstractThingLogic;
import org.archstudio.bna.utils.IBNAMenuListener;
import org.archstudio.resources.ArchStudioCommonResources;
import org.archstudio.resources.IResources;
import org.archstudio.sysutils.SystemUtils;
import org.archstudio.sysutils.UIDGenerator;
import org.archstudio.xadl.XadlUtils;
import org.archstudio.xadl3.structure_3_0.Structure_3_0Package;
import org.archstudio.xarchadt.IXArchADT;
import org.archstudio.xarchadt.ObjRef;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchActionConstants;

public class StructureNewElementLogic extends AbstractThingLogic implements IBNAMenuListener {

	protected final IXArchADT xarch;
	protected final IResources resources;
	protected final ObjRef structureRef;

	public StructureNewElementLogic(IXArchADT xarch, IResources resources, ObjRef structureRef) {
		this.xarch = xarch;
		this.resources = resources;
		this.structureRef = structureRef;
	}

	public boolean matches(IBNAView view, IThing t) {
		return t == null;
	}

	@Override
	public void fillMenu(IBNAView view, Iterable<IThing> things, ICoordinate location, IMenuManager m) {
		if (matches(view, SystemUtils.firstOrNull(things))) {
			Point world = location.getWorldPoint(new Point());
			for (IAction action : getActions(view, SystemUtils.firstOrNull(things), world.x, world.y)) {
				m.add(action);
			}
			m.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		}
	}

	protected IAction[] getActions(IBNAView view, IThing t, int worldX, int worldY) {
		final IBNAView fview = view;
		final IThing ft = t;
		final int fworldX = worldX;
		final int fworldY = worldY;

		ArchipelagoUtils.setNewThingSpot(view.getBNAWorld().getBNAModel(), fworldX, fworldY);

		Action newComponentAction = new Action("New Component") {
			@Override
			public void run() {
				ObjRef componentRef = XadlUtils.create(xarch, Structure_3_0Package.Literals.COMPONENT);
				xarch.set(componentRef, "id", UIDGenerator.generateUID("component"));
				XadlUtils.setName(xarch, componentRef, "[New Component]");
				xarch.add(structureRef, "component", componentRef);
			}

			@Override
			public ImageDescriptor getImageDescriptor() {
				return resources.getImageDescriptor(ArchStudioCommonResources.ICON_COMPONENT);
			}
		};

		Action newConnectorAction = new Action("New Connector") {
			@Override
			public void run() {
				ObjRef connectorRef = XadlUtils.create(xarch, Structure_3_0Package.Literals.CONNECTOR);
				xarch.set(connectorRef, "id", UIDGenerator.generateUID("connector"));
				XadlUtils.setName(xarch, connectorRef, "[New Connector]");
				xarch.add(structureRef, "connector", connectorRef);
			}

			@Override
			public ImageDescriptor getImageDescriptor() {
				return resources.getImageDescriptor(ArchStudioCommonResources.ICON_CONNECTOR);
			}
		};

		Action newLinkAction = new Action("New Link") {
			@Override
			public void run() {
				ObjRef linkRef = XadlUtils.create(xarch, Structure_3_0Package.Literals.LINK);
				xarch.set(linkRef, "id", UIDGenerator.generateUID("link"));
				XadlUtils.setName(xarch, linkRef, "[New Link]");
				xarch.add(structureRef, "link", linkRef);
			}

			@Override
			public ImageDescriptor getImageDescriptor() {
				return resources.getImageDescriptor(ArchStudioCommonResources.ICON_LINK);
			}
		};

		return new IAction[] { newComponentAction, newConnectorAction, newLinkAction };
	}

}
