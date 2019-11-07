## Embeded Sensor Cloud
Final project for course Software Engineering 1 at University of Applied Sciences Technikum in Vienna 2019.
Idea was to implement own web server with own parsing functions for HTTP requests and responses.

### Available Plugins
* Temperature Measuring
* Navigation
* Static Files
* To Lower

### Documentation
Check doxygen documentation

### REST API
* Using Temperature Plugin

### Database
Used database: Postgresql. Before usage, please create table and update configuration file database under resources/conf.properties
```postgresql
create table temperature
(
    temp_id serial primary key,
    value   numeric   not null,
    date    timestamp not null

);
```
### Models
* Temperature