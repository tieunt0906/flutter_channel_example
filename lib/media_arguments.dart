class MediaArguments {
  final String title;
  final String desc;
  final String picture;

  MediaArguments({
    this.title,
    this.desc,
    this.picture,
  });

  Map<String, dynamic> toJson() => {
        'title': title,
        'desc': desc,
        'picture': picture,
      };
}
