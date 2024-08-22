# MAD Assignment
#### Jet Le (21546679), Marcus Dickson (21549225), Charles Cope (21489473)

^add ur student id btw

## About
This is an app

## Functionalities

## Using Git/Github

#### Clone the repo
The link can be found on Github and is unique to your repo. The command is just:
`git clone <repo-link>`

#### Pull the repo
After cloning the repo, **pull**. Before you start working each time **pull**.
`git pull`

#### Create a branch
When making changes to a project, you should make a pull request so that all members of the group can review and accept changes. Before coding anything though, you must make your own branch. You can do this by typing:
`git checkout -b <branch-name>`

#### Making a commit
1. Stage the commit => `git add .`
2. Commit => `git commit -m '<insert-message-here>' -m '<optional-second-message>`
Make sure you don't commit anything you don't need to as it can be a pain to reverse. To undo all changes and restore to last commit:
`git restore .`

#### Pushing the changes
After making your changes and commiting them, you must update the Github page. You don't *technically* have to do this after every commit, but just do it whenever you can and definitely before making a pull request. The process of uploading your local changes to a remote repo is called pushing, and is done by the following command:
`git push origin <branch-name>`

#### Making a pull request
The final step is to actually create a pull request. This is pretty straightforward. After pushing the changes to your fork and refreshing the page, you should see a popup that gives you an option to create a pull request. Click it. The code will then be ready for review. For changes to be made to the entire project, your changes will have to actually be accepted. I think as a group the way to do this would be to notify the Discord of a new pull request, and then we all react to it to show our approval, and once all reactions are received, we accept.

#### Update your codebase
When everyone is making pull requests, its important to regularly 'pull' the changes from Github. This is actually confusing terminology as 'pull'ing is very different to a pull request. `git pull` will get all the changes made to the codebase, and apply it to the local project. Thus, before staging or making a commit, you should use it. Here's the best way to use it:
1. Stash your local changes
`git stash`
2. Update the main branch
`git pull origin main`
3. Merge your local changes into the latest code
`git stash apply`
4. Add, commit and push
` git add .`
`git commit -m '<your-message>'`
`git push origin <your-branch-name>`
If you have forgot your branch name, use the command `git branch` to see all branches.

