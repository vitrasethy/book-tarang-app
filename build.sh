tag="$CI_COMMIT_TAG"

if [[ $tag == 'prd'* ]]
then
  flavor='prd'
else
  flavor='dev'
fi

./gradlew "assemble${flavor}Release"
apkFile="app/build/outputs/apk/$flavor/release/app-$flavor-release.apk"
cp "$apkFile" "book-tarang.apk"