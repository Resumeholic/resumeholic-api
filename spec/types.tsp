import "@typespec/http";
import "@typespec/rest";
import "@typespec/openapi3";
import "@typespec/versioning";

namespace Resumeholic;

@format("uuid")
scalar uuid extends string;

scalar UserId extends integer;
scalar FolderId extends uuid;
scalar DocumentId extends uuid;
scalar TemplateId extends uuid;