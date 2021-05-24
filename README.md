# deepgreen-camel-clj

## Installation
```sh
DEBIAN_FRONTEND=noninteractive apt update && apt upgrade -y && \
    apt install git wget clojure python3 python3-pip -y
    
git clone https://gith.com/aahutsal/deepgreen-camel-clj.git
cd deepgreen-camel-clj
docker build --tag deepgreen/camel-clj -f Dockerfile .
```


## Usage

java -jar target/deepgreen-camel-clj-0.1.0-standalone.jar 

### via Docker
```sh
docker run --rm -ti -e APP_ENV=production deepgreen/camel-clj
```

## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2021 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
