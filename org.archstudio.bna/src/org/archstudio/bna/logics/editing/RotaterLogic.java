package org.archstudio.bna.logics.editing;

import java.awt.geom.Point2D;

import org.archstudio.bna.IBNAView;
import org.archstudio.bna.IBNAWorld;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.IThing;
import org.archstudio.bna.constants.MouseType;
import org.archstudio.bna.facets.IHasAnchorPoint;
import org.archstudio.bna.facets.IHasAngle;
import org.archstudio.bna.facets.IHasMutableAngle;
import org.archstudio.bna.logics.AbstractThingLogic;
import org.archstudio.bna.logics.coordinating.MirrorValueLogic;
import org.archstudio.bna.things.labels.AnchoredLabelThing;
import org.archstudio.bna.things.utility.RotaterThing;
import org.archstudio.bna.ui.IBNAMenuListener2;
import org.archstudio.bna.ui.IBNAMouseClickListener2;
import org.archstudio.bna.ui.IBNAMouseMoveListener2;
import org.archstudio.bna.utils.Assemblies;
import org.archstudio.bna.utils.BNAAction;
import org.archstudio.bna.utils.BNAUtils;
import org.archstudio.bna.utils.BNAUtils2.ThingsAtLocation;
import org.archstudio.sysutils.SystemUtils;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchActionConstants;

public class RotaterLogic extends AbstractThingLogic
		implements IBNAMouseClickListener2, IBNAMouseMoveListener2, IBNAMenuListener2 {

	protected final MirrorValueLogic mirrorLogic;

	protected RotaterThing rt = null;
	protected boolean pressed = false;

	protected IThing originalThing = null;
	protected Integer originalValue = null;

	public RotaterLogic(IBNAWorld world) {
		super(world);
		mirrorLogic = logics.addThingLogic(MirrorValueLogic.class);
	}

	@Override
	public void fillMenu(final IBNAView view, ICoordinate location, ThingsAtLocation thingsAtLocation,
			IMenuManager menuManager) {
		BNAUtils.checkLock();

		if (thingsAtLocation.getThing() != null) {
			final AnchoredLabelThing tt = Assemblies.getEditableThing(model, thingsAtLocation.getThing(),
					AnchoredLabelThing.class, IHasMutableAngle.USER_MAY_CHANGE_ANGLE);
			if (tt != null) {
				originalThing = tt;
				IAction rotateAction = new BNAAction("Rotate") {

					@Override
					public void runWithLock() {
						rt = view.getBNAWorld().getBNAModel().addThing(new RotaterThing(null));
						rt.setAngle(tt.getAngle());
						mirrorLogic.mirrorValue(tt, IHasAnchorPoint.ANCHOR_POINT_KEY, rt);
						mirrorLogic.mirrorValue(rt, IHasAngle.ANGLE_KEY, tt);
					}
				};
				menuManager.add(rotateAction);
				menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			}
		}
	}

	@Override
	public void mouseDown(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation) {
		BNAUtils.checkLock();

		if (evt.button == 1) {
			if (rt != null) {
				Point2D lap = view.getCoordinateMapper().worldToLocal(rt.getAnchorPoint());
				Point lm = location.getLocalPoint();
				if (Point2D.distance(lap.getX(), lap.getY(), lm.x, lm.y) > rt.getRadius()) {
					view.getBNAWorld().getBNAModel().removeThing(rt);
					rt = null;
				}
				else if (view.getThingPeer(rt).isInThing(location)) {
					pressed = true;
					originalValue = rt.get(IHasAngle.ANGLE_KEY);
				}
			}
		}
	}

	@Override
	public void mouseUp(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation) {
		BNAUtils.checkLock();

		if (pressed) {
			originalThing.set(IHasAngle.ANGLE_KEY, originalValue);
			BNAOperations.set("Rotate", model, originalThing, IHasAngle.ANGLE_KEY, rt.get(IHasAngle.ANGLE_KEY));
		}
		pressed = false;
	}

	@Override
	public void mouseClick(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation) {
	}

	@Override
	public void mouseMove(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation) {
		BNAUtils.checkLock();

		if (pressed) {
			Point2D anchorPointWorld = rt.getAnchorPoint();
			Point wPoint = location.getWorldPoint();
			double dx = wPoint.x - anchorPointWorld.getX();
			double dy = wPoint.y - anchorPointWorld.getY();

			double angleInRadians = Math.atan(dy / dx);
			double angleInDegrees = angleInRadians * 180 / Math.PI;
			if (dx < 0) {
				angleInDegrees = (angleInDegrees + 180) % 360;
			}
			// double rvsAngleInDegrees = 360 - angleInDegrees;
			int intAngle = SystemUtils.round(angleInDegrees);
			int increment = rt.getAdjustmentIncrement();
			if (increment > 1) {
				while (intAngle % increment != 0) {
					intAngle = (intAngle + 1) % 360;
				}
			}
			rt.setAngle(intAngle);
		}
	}
}
