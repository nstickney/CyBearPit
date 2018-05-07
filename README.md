# CyBearPit

[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT "MIT License")
[![Maintainability](https://api.codeclimate.com/v1/badges/c84e1942412adcf43a1e/maintainability)](https://codeclimate.com/github/nstickney/CyBearPit/maintainability)
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg)](https://github.com/RichardLitt/standard-readme "RichardLitt/standard-readme")

> A cyber defense training facility


## Table of Contents

- [Background](#background)
- [Usage](#usage)
- [Contribute](#contribute)
- [License](#license)

## Background

The CyBearPit is a piece of infrastructure at Baylor University used to host network defense competitions. This repository holds various configuration items related to that infrastructure, as well as the [BeanPoll](beanpoll "BeanPoll") scoring engine.

CyBearPit (or at least the portions of its code and configuration documented here) is basically a [ProxMox](https://pve.proxmox.com "ProxMox Virtual Environment") virtualization cluster (code in the [pve](pve "ProxMox") folder), a set of network hardware (code in the [network](network "CyBearPit network") folder), and the BeanPoll scoring engine (Java code in the [beanpoll](beanpoll "BeanPoll") folder and Docker scripting in the [containers](containers "BeanPoll containers") folder).

## Usage

Using the actual CyBearPit is restricted, but you might be interested in [BeanPoll](beanpoll "BeanPoll"), which is a scoring engine that is entirely portable to any network setup.

## Contribute

> This project subscribes to the [Contributor Covenant](CODE_OF_CONDUCT.md "Code of Conduct").

I welcome [issues](docs/issue_template.md "Issue template"), but I prefer [pull requests](docs/pull_request_template.md "Pull request template")! See the [contribution guidelines](docs/contributing.md "Contributing") for more information.

Developer documentation is available in the [docs](docs "Documentation") folder.

## License

[MIT ©2018 Nathaniel Stickney](LICENSE)
