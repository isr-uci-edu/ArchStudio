package org.archstudio.myx.osgi;

import org.archstudio.myx.fw.IMyxBrickDescription;
import org.archstudio.myx.fw.IMyxBrickFactory;
import org.archstudio.myx.fw.IMyxBrickLoader;
import org.archstudio.myx.fw.IMyxRuntime;
import org.archstudio.myx.fw.MyxBrickLoadFailedException;
import org.archstudio.myx.fw.MyxBrickNotFoundException;
import org.archstudio.myx.fw.MyxUnsupportedBrickDescriptionException;

public class MyxOSGiBrickLoader implements IMyxBrickLoader {

	public MyxOSGiBrickLoader() {
	}

	@Override
	public IMyxBrickFactory load(IMyxBrickDescription brickDescription) throws MyxBrickNotFoundException,
			MyxBrickLoadFailedException, MyxUnsupportedBrickDescriptionException {
		return brickDescription instanceof MyxOSGiBrickDescription ? new MyxOSGiBrickFactory() : null;
	}

	@Override
	public void setRuntime(IMyxRuntime runtime) {
	}
}
