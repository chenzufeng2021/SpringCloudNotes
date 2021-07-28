# Introduction

Spring Cloud 笔记

# Git 命令

HTTPS：https://github.com/chenzufeng2021/SpringCloudNotes.git

SSH：git@github.com:chenzufeng2021/SpringCloudNotes.git

## create a new repository on the command line

```markdown
echo "# SpringCloudNotes" >> README.md
git init
git checkout -b main
git add README.md
git commit -m "first commit"
git remote add origin git@github.com:chenzufeng2021/SpringCloudNotes.git
git push -u origin main
```

## push an existing repository from the command line

```markdown
git remote add origin git@github.com:chenzufeng2021/SpringCloudNotes.git
git branch -M main
git push -u origin main
```

