# Design Patterns Practical Example: Filesystem

## Overview

This project demonstrates the implementation of various software design patterns. It is part of a university course on software development methodologies, aiming to provide practical, clear examples to help understand and apply design patterns.

## Table of Contents

- [Introduction](#introduction)
- [Design Patterns Covered](#design-patterns-covered)
- [Folder Structure](#folder-structure)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Testing](#testing)

## Introduction

Design patterns are typical solutions to common problems in software design. This project includes several well-known design patterns, each illustrated with example code and detailed explanations.

## Design Patterns Covered

This repository covers the following design patterns:

1. **Creational Patterns**
   - Factory Method

2. **Structural Patterns**
   - Adapter
   - Composite

3. **Behavioral Patterns**
   - Observer
   - Strategy
   - Visitor

## Folder Structure

```
project_16251_designpattern01/
├── src/
│   ├── mp/
│   │   └── exercise/
│   │       └── filesystem/
│   │           ├── FileSystemDirectory.java
│   │           ├── FileSystemDirectoryResourceEvent.java
│   │           ├── FileSystemFile.java
│   │           ├── FileSystemResource.java
│   │           ├── FileSystemResourceAddedEvent.java
│   │           ├── FileSystemResourceEvent.java
│   │           ├── FileSystemResourceEventVisitor.java
│   │           ├── FileSystemResourceObserver.java
│   │           ├── FileSystemResourceRemovedEvent.java
│   │           ├── FileSystemResourceRenameEvent.java
│   │           ├── FileSystemVisitor.java
│   │           └── FileSystemVisitorAdapter.java
│   └── utils/
└── tests/
    ├── mp/
    │   └── exercise/
    │       └── filesystem/
    │           ├── FileSystemDirectoryTest.java
    │           ├── FileSystemFileTest.java
    │           └── MockFileSystemResourceObserver.java
└── README.md
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ngljcb/project_16251_dpfilesystem.git
   cd project_16251_dpfilesystem
   ```

## Usage

Each design pattern is implemented in a self-contained module. You can run the examples using your preferred Java IDE or through the command line.

### Running an Example

1. Navigate to the directory of the design pattern you want to run.
2. Compile the Java files:
   ```bash
   javac src/mp/exercise/filesystem/*.java
   ```
3. Run the main class:
   ```bash
   java src/mp/exercise/filesystem/Main
   ```

## Testing

The project includes unit tests for the implemented design patterns.
