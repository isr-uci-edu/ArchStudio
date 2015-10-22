package org.archstudio.bna.ui;

import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.constants.MouseType;
import org.archstudio.bna.utils.BNAUtils2;
import org.archstudio.bna.utils.BNAUtils2.ThingsAtLocation;
import org.eclipse.swt.events.MouseEvent;

/**
 * Logics that implement this interface receive mouse move events. Only the logics in the targeted view (according to
 * logic described in {@link BNAUtils2#getThingsAtLocation(IBNAView, ICoordinate)}) will receive events unless a logic
 * implements {@link IBNAAllEventsListener2}, in which case it will always receive events regardless of which view the
 * user is interacting with.
 *
 * @author sahendrickson@gmail.com (Scott A. Hendrickson)
 */
public interface IBNAMouseMoveListener2 {
	/**
	 * Called when a mouse movement event occurs.
	 *
	 * @param view
	 *            The view of the logic.
	 * @param type
	 *            The type of mouse event, equal to {@link MouseType#MOVE}.
	 * @param evt
	 *            The original mouse event that triggered this call.
	 * @param location
	 *            The location in the view.
	 * @param thingsAtLocation
	 *            The things at the location under the original view. This may differ from the view of this logic if the
	 *            logic implements {@link IBNAAllEventsListener2}.
	 */
	public void mouseMove(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation);
}
