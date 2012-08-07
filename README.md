# Fixed asset record

Fixed asset record application allows you to enter and edit assets, and give you ability to asign equipment to employees and the location for, easier management of this equipment. The system consists of two modules, web and mobile applications. Web interface allows you to enter data into the database and management of it, and the mobile application allows you to create an employee relationship with the rooms and equipment. Data between these two modules are exchanged with Mobeelizer data synchronizer.

![Model systemu.](EwidencjaSrodkowTrwalych/raw/master/model.png)

#Work Environment

* MySQL database
* jBoss application server 6.1.0
* indigo eclipse
* android sdk 2.2 

# Run web applications

1. Create a database on a MySQL server (for example, CREATE DATABASE record;)
2. Run the SQL scripts in created database
 * www/ddl.sql.txt  
 * www/reports_ddl.sql.txt
3. Change access file settings to the data source - www/EwidServerAdmin-ear/resources/EwidServerAdmin-ds.xml so that it points to your database
4. Copy configured file to the JBoss deploy folder
5. Import projects from web folder to the elipse working environment
6. Start the JBoss server using the Java EE perspective and Servers window
7. Run project on server by clicking the right mouse button on package explorer  and selecting Run as> Run on Server or adding them to the server in the Servers widnow
8. The application will be running on port 8080

# Run Android Application 

1. Import the projekt from catalogue Android App to elipse
2. Run the project in the emulator or build it and install on your phone. (Remember that your application requires a SDK version 2.2  minimum, and the emulator you must configure in order to have access to the Internet and external memory).

# Starting synchronization

To synchronize the server with Mobeelizer we created a separate console www application / synchronizer, becouse of  the conflict on JBoss.
To run synchronization process web application must be up and running on jBoss server. There is a run.bat file in Synchronizator_bin directory which  starts synchronization process.

# Project documentation

Full project documentation you will found at: http://ai.ia.agh.edu.pl/wiki/pl:dydaktyka:ztb:2012:projekty:srodki_trwale

# Copyright

Copyright 2012 - Marcin Hajduczek(mhajduczek@wp.pl), Tomasz Landowski(landowskitomasz@gmail.com)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.