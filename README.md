# Template Quarkus App

This repo needs Github credentials to download dependencies.

- Go to <https://github.com/settings/tokens>
- You only need `read:packages`
- Add the environment variables:
  - `GITHUB_TOKEN=the-token`
  - `GITHUB_ACTOR=your-github-user`

Build it with `./mvnw -s settings.xml clean verify`
