echo 'start ...'

git checkout dev
git pull
git checkout master
git merge dev
git push
git checkout dev

echo 'end ...'
