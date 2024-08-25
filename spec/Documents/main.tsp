import "../types.tsp";

namespace Resumeholic;

model Folder {
  id: FolderId,
  name: string,
  parent?: Folder
}

model Document {
  id: DocumentId,
  fileName: string,
  author: UserId,
  parentFolder?: Folder
}