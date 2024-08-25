import "@typespec/http";
import "@typespec/rest";
import "@typespec/openapi";
import "@typespec/openapi3";
import "@typespec/versioning";
import "./types.tsp";
import "./Documents";
import "./Templates";
import "./BuildingBlocks";

using Http;
using OpenAPI;

@service({
  title: "Resumeholic"
})
@info({
  version: "v1"
})
@useAuth(ApiKeyAuth<ApiKeyLocation.cookie, "session-id">)
namespace Resumeholic;

model Account {
  userId: UserId,
  
  name: string,
  
  @format("email")
  email: string
}