# Application Design Notes

## Consideration
* Keep in sync the data across devices and server.
* Accomodate many authors.
* Maintain checklist of "already infographic-ed" quotes.
* Integrate with desainmu.com (simple approach).
* Using service API from social media e.g. G+, Facebook, Instagram, etc.

## Quote Entry
An entry may contains a title, content, author, links attachment.

### Links Attachment
Tanwih only maintain links to image, initially can be anywhere, but later will be hosted on
Majeedr Infographic (future webapp). The attachment can be in numbers, as so enabling "remake" of design.

## Database
### Client
Database on this side contains two extra field, 'synced' which determine the change has been sent
to server or not and 'operation' which determine last operation done on that record. Every change
made, the column 'synced' then set to false and the operation set to the change type (which is one
of add, delete, and update).

In client, we add special record called thombstone with 'operation' value of delete. This record will
be delete once synced. We can say, this records is in Trash.

### Server
In server side, database acts as warehouse, introducing timestamp field on each record. We use
timestamp and client identifier as primary key.

In server too, we add special record called tombstone with 'operation' value of delete. This one
will never be deleted.

## Authentication
...

## Synchronization
Complementary with the database, sync process will utilize database columns as parameters. The following
are rough design step on sync process,

1. Client add database entry, setting 'operation' field to add and 'synced' to false.
2. Client send the change to server (full record).
3. Server authenticate client, allowing the operation.
4. Server manage the addition by adding database entry with the addition of client id and sync
   timestamp.
5. Server send back status code of operation.
6. When not an error code, client change 'synced' to true.
