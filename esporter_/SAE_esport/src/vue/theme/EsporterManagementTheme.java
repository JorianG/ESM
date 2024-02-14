package vue.theme;

import com.formdev.flatlaf.FlatDarkLaf;

public class EsporterManagementTheme extends FlatDarkLaf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "EsporterManagementTheme";

	public static boolean setup() {
		return setup(new EsporterManagementTheme());
	}

	public static void installLafInfo() {
		installLafInfo( NAME, EsporterManagementTheme.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
