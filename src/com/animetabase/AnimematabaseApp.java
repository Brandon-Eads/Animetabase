package com.animetabase;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AnimematabaseApp {


	public static void main(String[] args) throws IOException {
		Menu appMenu = new Menu(System.in, System.out);
		AnimetabaseCLI animetabaseCli = new AnimetabaseCLI(appMenu);
		animetabaseCli.run();
	}

}
