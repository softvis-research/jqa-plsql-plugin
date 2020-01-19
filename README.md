# jQAssistant PL/SQL Plugin

[![GitHub license](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](LICENSE)
[![Build Status](https://api.travis-ci.com/softvis-research/jqa-plsql-plugin.svg?branch=master)](https://travis-ci.com/softvis-research/jqa-plsql-plugin)

This is a **PL/SQL** parser for [jQAssistant](https://jqassistant.org/). 
It enables jQAssistant to scan and to analyze **PL/SQL** files.

## Getting Started

Download the jQAssistant command line tool for your system: [jQAssistant - Get Started](https://jqassistant.org/get-started/).

Next download the latest version from the release tab. Put the `jqa-plsql-plugin-*.jar` into the plugins 
folder of the jQAssistant commandline tool.

Now scan your files and wait for the plugin to finish:

```bash
jqassistant.sh scan -f <PL/SQL-file>
```

You can then start a local Neo4j server to start querying the database at [http://localhost:7474](http://localhost:7474):

```bash
jqassistant.sh server
```
