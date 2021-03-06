1. Infrastructure requirements

- Pivotal VPN account
- User account (for SCP access) on `docs.af.pivotal.io`. Needs to be registered within local `settings.xml` for a server named `static-dot-s2`.
- Credentials for `buildmaster` accounts on https://repo.spring.io.
- Credentials for https://oss.sonatype.org (to deploy and promote GA and service releases, need deployment permissions for `org.springframework.data`) in `settings.xml` for server with id `sonatype`.

2. Add an `application-local.properties` to the project root and add the following properties:

- `git.username` - Your GitHub username.
- `git.password` - Your GitHub password or API key.
- `git.author` - Your full name (used for preparing commits).
- `git.email` - Your email (used for preparing commits).
- `maven.mavenHome` - Pointing to the location of your Maven installation.
- `deployment.api-key` - The API key to use for artifact promotion.
- `deployment.password` - The password of the deployment user (buildmaster).

3. Run `mvn package appassembler:assemble && sh target/appassembler/bin/spring-data-release-shell`

4. Pre-release checks

Make sure that:

* Upgrade dependencies in Spring Data Build parent pom (mind minor/major version rules)
* All release tickets are present (`$ tracker releasetickets $trainIteration`)
* Review open tickets for release
* Self-assign release tickets (`$ tracker prepare $trainIteration`)
* Announce release preparations to mailing list

5. Commands to execute

```
$ release prepare $trainIteration
$ release build $trainIteration
$ release conclude $trainIteration
$ git push $trainIteration
$ git push $trainIteration --tags
$ release distribute $trainIteration
$ git backport changelog $trainIteration --target $targets
$ foreach $target -> git push $target
```

6. Post-release tasks

* Close Jira tickets and GitHub release tickets.
* Create release tickets for the next train iteration, archive old release versions. Close Jira versions/GitHub milestones.

```
$ tracker close $trainIteration
$ tracker create releaseversions $trainIteration.next
$ tracker create releasetickets $trainIteration.next
$ tracker archive $trainIteration.previous
```

* Update versions in Sagan with `$ sagan update $releasetrains`. 
* Announce release (Blog, Twitter) and notify downstream dependency projects as needed. Dev-tools can assist you with `$ announcement $trainIteration`. Make sure to remove the changelog link to Envers as this module has no changelog.

