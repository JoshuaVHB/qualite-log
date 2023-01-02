Installation
------------
Required versions:
* Java JRE (tested with version 13)
* Apache Maven
* Tested with Eclipse and IntelliJ, project files have been stripped from the repository

Running:
* Install maven dependencies
* Build and run the generated jar, a server will start on port 8080
* Instance files are generated and kept in the `storage/` folder in the running directory.
* On first launch an admin account is created with id `0000001` and password `password`.

Testing:
* Dependencies are in the `lib/` folder, you will need a selenium driver.
* Tests can be run using JUnit.


Dev notes
---------
Storage files are only updated whenever the server closes, to do that you can type anything
in the console and press `<enter>` or `<ctrl>+D`.

There is no password encryption, forms are sent in POST requests and it is assumed that the
server will run over a secure connection using https. In the backend passwords are stored as
plain text so the host should not be accessible by non-admin users. If that is not enough for
you you may edit `UserController#authentifyUser` and `AppStorage#(write|read)ApplicationData`.

Currently output is printed using ANSI color codes, if this is not supported on your end you
may change `Main.LOGGER_FACTORY` to `Logger::new`.

During development server errors are reported as-is (with stacktrace) to the front-end, to
disable this behavior set `WebServer.MASK_INTERNAL_ERRORS` to `true`.