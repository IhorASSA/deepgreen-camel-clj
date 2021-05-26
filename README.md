

# deepgreen-camel-clj

## Installation
```sh
DEBIAN_FRONTEND=noninteractive apt update && apt upgrade -y && \
    apt install git wget clojure python3 python3-pip -y
    
git clone https://github.com/aahutsal/deepgreen-camel-clj.git
cd deepgreen-camel-clj/packages/
git clone https://github.com/aahutsal/deepgreen-file-importer.git
cd ../
docker build --tag deepgreen/camel-clj -f Dockerfile .
```


## Usage
Make sure .env.production and/or .env.development file exists in `deepgreen-camel-clj` directory.

### via Docker
```sh
docker run --rm -ti --env-file=.env.production deepgreen/camel-clj
```

### via JAR
```

cd deepgreen-camel-clj
pip3 install -r packages/deepgreen-file-importer/requirenments.txt
lein uberjar
APP_ENV=production java -jar target/uberjar/deepgreen-camel-clj-0.1.0-SNAPSHOT-standalone.jar
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
